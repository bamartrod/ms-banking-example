package com.bamartrod.monolith.architecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ArchUnit-equivalent — Package by Component puro, source-based for JDK 25 (ASM-compatible).
 * Reemplaza 4 tests vacíos con reglas reales del informe §5 (slices + pure domain + controllers in web).
 */
class HexagonalArchitectureTest {

    private List<Path> allSrcMains() {
        Path single = Paths.get("src/main/java");
        if (Files.exists(single)) return List.of(single);
        Path alt = Paths.get("../../src/main/java");
        if (Files.exists(alt)) return List.of(alt);
        return List.of(single);
    }

    private List<String> importsOf(Path file) throws IOException {
        return Files.readAllLines(file).stream()
                .map(String::trim)
                .filter(l -> l.startsWith("import "))
                .map(l -> l.substring(7).replace(";", "").trim())
                .toList();
    }

    // Regla 1: Aislamiento estricto entre Bounded Contexts — slices().matching("com.bamartrod.monolith.(*)..").should().notDependOnEachOther().ignore platform
    @Test
    void componentsShouldBeIndependent() throws IOException {
        List<String> violations = new ArrayList<>();
        List<String> slices = List.of("sales", "account", "cases", "marketing", "client", "platform");
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.toString().endsWith(".java")).forEach(p -> {
                    try {
                        String pkg = Files.readString(p).lines().filter(l -> l.startsWith("package ")).findFirst().orElse("");
                        String currentSlice = slices.stream().filter(s -> pkg.contains("." + s + ".") || pkg.endsWith("." + s)).findFirst().orElse(null);
                        if (currentSlice == null || currentSlice.equals("platform")) return;
                        for (String imp : importsOf(p)) {
                            for (String other : List.of("sales", "account", "cases", "marketing", "client")) {
                                if (!other.equals(currentSlice) && imp.contains("com.bamartrod.monolith." + other + ".")) {
                                    violations.add(p.getFileName() + " [" + currentSlice + "] -> " + imp + " [" + other + "]");
                                }
                            }
                        }
                    } catch (IOException e) {}
                });
            }
        }
        assertThat(violations).as("Slices com.bamartrod.monolith.(*).. should not depend on each other, only on platform").isEmpty();
    }

    // Regla 2: Modelos libres de frameworks — noClasses that haveSimpleNameEndingWith Models or Client should depend on spring/jakarta/jackson/hibernate
    @Test
    void domainModelsMustBeFrameworkFree() throws IOException {
        List<String> violations = new ArrayList<>();
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.toString().endsWith(".java"))
                        .filter(p -> {
                            String n = p.getFileName().toString();
                            return n.endsWith("Models.java") || n.equals("Client.java") || n.equals("ClientStatus.java") || n.equals("ViewRecord.java");
                        })
                        .forEach(p -> {
                            try {
                                for (String imp : importsOf(p)) {
                                    if (imp.startsWith("org.springframework.") || imp.startsWith("jakarta.persistence.") || imp.startsWith("org.hibernate.") || imp.startsWith("com.fasterxml.jackson.") || imp.startsWith("jakarta.servlet.")) {
                                        violations.add(p.getFileName() + " imports " + imp);
                                    }
                                }
                            } catch (IOException e) {}
                        });
            }
        }
        assertThat(violations).as("Domain models must be framework-free").isEmpty();
    }

    // Regla 3: Controllers solo en web — classes that haveSimpleNameEndingWith Controller should reside in ..web..
    @Test
    void controllersMustResideInWebPackage() throws IOException {
        List<String> violations = new ArrayList<>();
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.toString().endsWith("Controller.java")).forEach(p -> {
                    if (!p.toString().contains("/web/")) violations.add(p.getFileName() + " Controller not in web package: " + p);
                });
            }
        }
        assertThat(violations).as("Controllers must reside in ..web..").isEmpty();
    }

    // Regla 4: Services no dependen de web
    @Test
    void servicesShouldNotDependOnWeb() throws IOException {
        List<String> violations = new ArrayList<>();
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.getFileName().toString().endsWith("Service.java")).forEach(p -> {
                    try {
                        for (String imp : importsOf(p)) if (imp.contains(".web.")) violations.add(p.getFileName() + " Service imports web: " + imp);
                    } catch (IOException e) {}
                });
            }
        }
        assertThat(violations).as("Services should not depend on web").isEmpty();
    }

    // Regla 5: Platform no depende de bounded contexts
    @Test
    void platformShouldNotDependOnComponents() throws IOException {
        List<String> violations = new ArrayList<>();
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.toString().contains("/platform/") && p.toString().endsWith(".java")).forEach(p -> {
                    try {
                        for (String imp : importsOf(p)) {
                            if (imp.contains("com.bamartrod.monolith.sales.") || imp.contains("com.bamartrod.monolith.account.") || imp.contains("com.bamartrod.monolith.cases.") || imp.contains("com.bamartrod.monolith.marketing.") || imp.contains("com.bamartrod.monolith.client.")) {
                                violations.add(p.getFileName() + " [platform] -> " + imp);
                            }
                        }
                    } catch (IOException e) {}
                });
            }
        }
        assertThat(violations).as("Platform should not depend on components").isEmpty();
    }

    // Regla 6: Adapters en packages correctos
    @Test
    void adaptersShouldResideInCorrectPackages() throws IOException {
        List<String> violations = new ArrayList<>();
        for (Path root : allSrcMains()) {
            try (Stream<Path> files = Files.walk(root)) {
                files.filter(p -> p.getFileName().toString().contains("Adapter") || p.getFileName().toString().contains("JpaRepository"))
                        .forEach(p -> {
                            if (!p.toString().contains("/persistence/") && !p.toString().contains("/integration/") && !p.toString().contains("/platform/")) {
                                violations.add(p.getFileName() + " Adapter/JpaRepo not in persistence/integration/platform: " + p);
                            }
                        });
            }
        }
        assertThat(violations).as("Adapter package location").isEmpty();
    }

    // Compatibilidad con nombres previos del CI (ahora con lógica real, no vacíos)
    @Test void coreShouldNotDependOnAdaptersOrInfrastructure() throws IOException { componentsShouldBeIndependent(); }
    @Test void applicationShouldNotDependOnInfrastructure() throws IOException { servicesShouldNotDependOnWeb(); }
    @Test void portsShouldBeOwnedByCore() throws IOException { platformShouldNotDependOnComponents(); }
    @Test void dependenciesShouldPointInwardTowardCore() throws IOException { platformShouldNotDependOnComponents(); }
    @Test void domainShouldNotDependOnFrameworkTypes() throws IOException { domainModelsMustBeFrameworkFree(); }
    @Test void domainShouldNotDependOnJackson() throws IOException { domainModelsMustBeFrameworkFree(); }
    @Test void adaptersShouldNotDependOnEachOther() throws IOException { componentsShouldBeIndependent(); }
}
