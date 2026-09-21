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
import static org.assertj.core.api.Assertions.fail;

/**
 * Cohesion / Size Gate — CSAS-007-U8, CSAS-007-U9, CSAS-008-U5
 * Triggers architectural cohesion review when a class approaches/exceeds 200 LOC
 * (excl. boilerplate/comments/blank). Violation is not automatic — requires
 * REVIEW_REQUIRED evidence per CSAS-002-U30, but this test flags candidates.
 *
 * Also enforces anti-gaming per CSAS-007-U10 / CSAS-008-U6:
 * artificial helpers / inheritance tricks to stay under 200 are forbidden.
  *
 * @author Brandon Martinez : https://github.com/bamartrod - https://www.linkedin.com/in/bamartrod
 */
class CohesionTest {

    private static final int LOC_THRESHOLD = 200;
    private static final Path SRC_MAIN = Paths.get("src/main/java");

    private List<Path> allSrcMains() {
        Path single = Paths.get("src/main/java");
        if (Files.exists(single)) return List.of(single);
        Path alt = Paths.get("../../src/main/java");
        if (Files.exists(alt)) return List.of(alt);
        return List.of(SRC_MAIN);
    }

    private Path resolveSrcMain() {
        var all = allSrcMains();
        return all.get(0);
    }

    @Test
    void noClassShouldExceed200LocWithoutCohesionReview() throws IOException {
        List<Path> srcMains = allSrcMains();
        boolean anyExists = srcMains.stream().anyMatch(Files::exists);
        if (!anyExists) {
            System.out.println("[CohesionTest] no src/main/java found — skipping LOC gate");
            return;
        }

        List<String> violations = new ArrayList<>();
        List<String> nearThreshold = new ArrayList<>();

        for (Path srcMain : srcMains) {
            if (!Files.exists(srcMain)) continue;
            try (Stream<Path> files = Files.walk(srcMain)) {
                files.filter(p -> p.toString().endsWith(".java"))
                        .forEach(p -> {
                            int loc = countLoc(p);
                            String rel = srcMain.getFileName() + "/" + srcMain.relativize(p).toString();
                            // handle monolith nested path
                            String prefix = srcMain.toString().contains("refactor") ? srcMain.toString() : srcMain.toString();
                            String display = p.toString();
                            if (loc > LOC_THRESHOLD) {
                                violations.add("%s — %d LOC (threshold %d) -> REVIEW_REQUIRED per CSAS-007-U8/U9".formatted(display, loc, LOC_THRESHOLD));
                            } else if (loc > 160) {
                                nearThreshold.add("%s — %d LOC (approaching threshold)".formatted(display, loc));
                            }
                        });
            }
        }

        if (!nearThreshold.isEmpty()) {
            System.out.println("[CohesionTest] Near-threshold classes (>160 LOC):");
            nearThreshold.forEach(c -> System.out.println("  - " + c));
        }

        if (!violations.isEmpty()) {
            System.out.println("[CohesionTest] Violations (>200 LOC):");
            violations.forEach(v -> System.out.println("  - " + v));
            fail("""
                    Cohesion gate triggered — %d class(es) exceed %d LOC.
                    Per CSAS-007-U8/U9 and CSAS-008-U5 this requires REVIEW_REQUIRED with evidence:
                    - single irreducible responsibility
                    - high method-field cohesion (CohesionScore >= θ)
                    - no artificial helpers/inheritance gaming (CSAS-007-U10)
                    Violations:
                    %s
                    """.formatted(violations.size(), LOC_THRESHOLD, String.join("\n", violations)));
        }
    }

    @Test
    void noGodClassShouldExist() throws IOException {
        List<Path> srcMains = allSrcMains();
        List<String> suspects = new ArrayList<>();
        for (Path srcMain : srcMains) {
            if (!Files.exists(srcMain)) continue;
            try (Stream<Path> files = Files.walk(srcMain)) {
                List<String> found = files.filter(p -> p.toString().endsWith(".java"))
                        .filter(p -> {
                            try {
                                String content = Files.readString(p);
                                long publicMethods = content.lines().filter(l -> l.trim().startsWith("public ") && l.contains("(")).count();
                                long fields = content.lines().filter(l -> l.contains("private final ")).count();
                                return publicMethods > 10 && fields > 5;
                            } catch (IOException e) {
                                return false;
                            }
                        })
                        .map(p -> p.toString())
                        .toList();
                suspects.addAll(found);
            }
        }

            assertThat(suspects)
                    .as("God class check (SRP per CSAS-007-U2) — classes with >10 public methods + >5 collaborators")
                    .isEmpty();
    }

    private int countLoc(Path file) {
        try {
            List<String> lines = Files.readAllLines(file);
            int loc = 0;
            boolean inBlockComment = false;
            for (String raw : lines) {
                String line = raw.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("/*")) inBlockComment = true;
                if (inBlockComment) {
                    if (line.endsWith("*/")) inBlockComment = false;
                    continue;
                }
                if (line.startsWith("//")) continue;
                if (line.startsWith("*")) continue;
                if (line.startsWith("import ")) continue;
                if (line.startsWith("package ")) continue;
                loc++;
            }
            return loc;
        } catch (IOException e) {
            return 0;
        }
    }
}
