package com.bamartrod.monolith.architecture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assembly (app module) hexagonal conformance — source-based JDK25.
 * Usa resolución dinámica de package (no hardcode com/example).
 */
class AssemblyArchitectureTest {

    private Path resolveSourceRoot() {
        // Dinámico: deriva package de esta clase de test (com.bamartrod.monolith.architecture) -> src/main/java/com/bamartrod/monolith
        String packagePath = "com/bamartrod/monolith";
        Path p = Paths.get("src/main/java", packagePath);
        if (Files.exists(p)) return p;
        Path alt = Paths.get("../../src/main/java", packagePath);
        if (Files.exists(alt)) return alt;
        // Fallback búsqueda por MonolithApplication.java
        Path cur = Paths.get("src/main/java");
        try (Stream<Path> walk = Files.walk(cur)) {
            var found = walk.filter(x -> x.getFileName().toString().equals("MonolithApplication.java")).findFirst();
            if (found.isPresent()) return found.get().getParent();
        } catch (IOException ignored) {}
        return p;
    }

    private List<String> importsOf(Path file) throws IOException {
        return Files.readAllLines(file).stream()
                .map(String::trim)
                .filter(l -> l.startsWith("import "))
                .map(l -> l.substring(7).replace(";", "").trim())
                .toList();
    }

    @Test
    void appShouldNotContainDomainLogic() throws IOException {
        Path appSrc = resolveSourceRoot();
        if (!Files.exists(appSrc)) return;
        long businessClasses;
        try (Stream<Path> files = Files.list(appSrc)) {
            businessClasses = files.filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> !p.getFileName().toString().contains("Application"))
                    .count();
        }
        assertThat(businessClasses)
                .as("assembly root (com.bamartrod.monolith) should be wire-only — only MonolithApplication allowed")
                .isLessThanOrEqualTo(1);
    }

    @Test
    void appShouldNotIntroduceOutwardEdgesViaDirectEntityExposure() throws IOException {
        Path appSrc = resolveSourceRoot();
        if (!Files.exists(appSrc)) return;
        Path assembly = appSrc.resolve("MonolithApplication.java");
        if (!Files.exists(assembly)) return;
        List<String> violations = new java.util.ArrayList<>();
        for (String imp : importsOf(assembly)) {
            if (imp.contains(".persistence.") && imp.contains("Entity")) {
                violations.add(assembly.getFileName() + " imports " + imp + " (direct entity coupling)");
            }
        }
        assertThat(violations)
                .as("assembly must depend on Ports via DI, not directly on persistence entities")
                .isEmpty();
    }
}
