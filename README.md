# ERT Gradle Plugin

## Overview

Today, it is not obvious how to apply version control to manage the resources in an Enonic CMS project. The Enonic Resource Tool is a tool to easen this task.

### Quickstart

1. Install gradle, one of the following: 
* Use the attached [gradlew.zip|ERT Documentation^gradlew.zip], unzip to local folder. Remember to set execute on file {{gradlew}} (chmod a+x gradlew). Use {{gradlew}} command instead of {{gradle}} in step 4,5 and 6.  
* Download and install from http://www.gradle.org 
* Install using macports from http://macports.org

2. Edit the sample configuration file: [sample build.gradle|ERT build.gradle sample] to match your environment, save as {{build.gradle}} to a local folder
3. Open shell and cd to above folder
4. run: "{{gradle tasks}}" to see available tasks
5. run: "{{gradle backupTest}}" to create local backup of test-server
6. run: "{{gradle syncLocal}}" to fetch test-server to local disk

### ERT Workflow

A typical flow using ERT will be:

*Development*
1. Check out resources from version control to local disk
2. Check that development environment is up to date 
3. Make changes to local files, apply changes to development environment as you go
4. Check in all changes to version control

*Test*
5. Check out resources to local disk
6. Run diff-task local -> test, verify changes
7. Run sync-task local -> test

*Prod*
8. Run backup-task to backup production to local disk
9. Check out resources to local disk
10. Run diff-task local -> test, verify changes
11. Run sync-task local -> prod

### Configuration

See [ERT Configuration]

## Oxygen integration

See [ERT - Oxygen integration]

## Known bugs

# Future features / Todo

See [ERT - Todo]

