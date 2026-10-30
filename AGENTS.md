# Project working instructions

## Preserve agreed design; no shortcuts

- Treat agreed design constraints as acceptance criteria. Do not bypass, weaken, or replace them to make implementation easier, obtain passing tests, or make the UI appear complete.
- Before implementing a fix, identify the underlying requirement and verify that the proposed change satisfies it. If a change requires departing from an agreed design, explain the conflict and obtain explicit agreement first; do not treat silence as approval.
- Add a prerequisite only when the dependent check genuinely needs the evidence or correctness established by that prerequisite. State that dependency concretely. Do not add dependencies merely to trigger execution or increase coverage.
- Expected-input checks run only their genuine prerequisite chains, consistently with A1. Independent regular and extra-credit checks remain independently selectable through their own suites or the full assignment suite. An unrun independent check is not a defect.
- Do not add run-everything helpers, zero-point execution entries, or equivalent side effects to bypass prerequisite design. Verify both required dependencies and independence from unrelated failures; do not substitute full-suite success for verification of the requested entry point.

- No fallbacks in any code written. Require explicit configuration and report missing or invalid inputs; do not silently substitute behavior or select an assignment.
- No magic numbers or strings. Use authoritative named definitions for meaningful file names, protocol values, settings, and behavioral constants; keep independent test expectations independent.
- Put shared code in shared packages. Do not duplicate tests or retain obsolete wrappers to accommodate assignment-specific configuration.
- Inspect related definitions and consumers before edits, reuse existing definitions, review touched code for duplication, and run relevant checks. Keep changes within the requested scope.

## Grading check names

- Use imports and simple class names in suite declarations, with one member per line.
- Place a suite beside its tests when they share one package. Keep cross-package groups separate and shared tests in common packages.

- Use no assignment prefix for unchanged shared checking rules.
- Use `A1` or `A2` for assignment-specific behavior and fixed groups of classes introduced by that assignment. A2 includes both assignments' class suites; do not duplicate their checking code.
- Use `Adaptive` for shared checks whose expectations adapt to actual user input and trace files. Suite configuration alone does not make a check adaptive.
- Keep reusable individual class-role checks descriptive; the containing A1/A2 class suites identify which assignment introduces those roles.

## Grading test documentation

- First write a comment in each new or changed grading test explaining its evidence, what it checks, and concrete success and failure examples. State when insufficient evidence earns no credit and distinguish that from inconsistent evidence.
- Then summarize that comment in the test's `@Explanation` text for the grading UI. Describe the implemented behavior and its limits; do not imply that a check proves more than it does.

## Released A1 suites and score preservation

- Keep released A1 suite definitions and scoring unchanged unless the user explicitly authorizes a change; a requested rename does not authorize changing membership or points.
- In the A2 UI, combine corresponding A1/A2 categories in flat A1A2 suites listing the existing atomic tests directly. Keep the original suites available separately; never nest them to create these groups.
- Do not change GraderBasics or patch its jar to implement assignment-suite organization. Preserve original leaf weights and extra-credit flags using the assignment rubric.
- The grading UI flattens nested suites and can lose nested suite-level points and extra-credit flags. Verify actual grading-tree scores and extra-credit status as well as leaf membership after reorganizing suites.
