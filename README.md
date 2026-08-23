# Ekud

A command-line task chatbot, started from the CS2103T Duke project template.

## AI assistance

Parts of this project were written with help from AI coding tools. Suggestions were reviewed and edited before being included.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/ekud/Ekud.java` file, right-click it, and choose `Run 'Ekud.main()'` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see the Ekud banner in the output.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Creating a fat JAR

This project uses the Gradle Shadow plugin to package Ekud as an executable fat JAR (`ekud.jar`). A fat JAR includes your classes and any runtime libraries, so someone else can run the app with only Java installed.

From the project root, with JDK 25:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-25.0.4"
.\gradlew.bat clean shadowJar
```

Or in IntelliJ: Gradle tool window → `Tasks` → `shadow` → `shadowJar`. Use `clean` first if you want Gradle to rebuild even when it thinks the JAR is up to date.

The file is created at `build/libs/ekud.jar`. Do not commit it; `/build/` in `.gitignore` already ignores that folder.

To run it, copy `ekud.jar` into an empty folder, open a terminal in that folder, and run:

```powershell
java -jar ekud.jar
```

The save file is created as `data/ekud.txt` relative to the folder you run from, not relative to the project source tree.
