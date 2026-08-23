# Project context

This repository is a starter template for a greenfield Java project used in an introductory software engineering course in an undergraduate computer science program. Students use it as the starting point for their own projects.

# Default user context

Unless the user says otherwise, assume that you are assisting a student working on a project in this repository. 
Assume the OS used is Windows.

# Guidance for interacting with users

* Explain the rationale for significant actions: what you did and why.

  * When suggesting a Git command, briefly explain what it does.
  * Add explanatory Javadoc comments to all classes and to nontrivial methods and fields when their purpose or behavior is not obvious.
  * Make generated code as self-explanatory as possible, and include explanatory comments where they improve understanding.
  * When faced with a design choice, choose the simplest option that is sufficient for the requirements, while briefly explaining relevant more advanced alternatives.

# Project-specific requirements

## Java version:

Ensure that Java 25 is used when running the application or build tasks. On macOS, use `sdk use java 25.0.3.fx-zulu` to switch to Java 25 if needed.

## Testing

This project uses JUnit 5. Test classes live under `src/test/java` and must mirror the main package and class name (`ekud.task.Todo` is tested by `ekud.task.TodoTest`).

After every code change that affects behaviour, public APIs, error messages, date/command formats, or save-file format, update the matching tests in the same change. Tests must stay green and must still describe the intended behaviour. Do not leave tests failing, skipped, or asserting outdated output.

If you add a new command, task type, parser rule, or storage format, add tests for the success path and the important invalid cases. Run `gradlew test` (Java 25) before considering the change done.

## Packaging

Create the distributable fat JAR with `gradlew clean shadowJar` (Java 25). The output is `build/libs/ekud.jar`. Do not commit JAR files; they are build artifacts. `/build/` in `.gitignore` already ignores the Gradle output directory.

## Git

Use lightweight tags unless the user requests an annotated tag.
When proposing or creating a commit message, include enough detail to explain the rationale for the change.
Do not commit or push unless explicitly asked.
