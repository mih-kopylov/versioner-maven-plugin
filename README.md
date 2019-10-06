# Versioner Maven Plugin

[![Build Status](https://travis-ci.com/mih-kopylov/versioner-maven-plugin.svg)](https://travis-ci.com/mih-kopylov/versioner-maven-plugin)
[![Maven Central](https://img.shields.io/maven-central/v/ru.mihkopylov/versioner-maven-plugin.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22ru.mihkopylov%22%20AND%20a:%22versioner-maven-plugin%22)
![License](https://img.shields.io/github/license/mih-kopylov/versioner-maven-plugin)

## Descripion

The goal of `Versioner Maven Plugin` is to provide a flexible configurable way for release management with respect to [semantic versionig](https://semver.org)

The idea is to describe each procedure as an actor, then combine those actors in a list and execute them one by one. 
There's a shared context (of type `Map<String, String>`) so that actors commmunicate with each other with it.

## Usage 

Assume the current project version is `1.2.3-SNAPSHOT` and one need to do a minor release.

In order to do that one need to:
* create a release branch like `release-1.3`
* change project version to `1.3.0`
* make a commit
* set `1.3.0` tag to that commit
* change project version to `1.3.1-SNAPSHOT` - the snapshot version of next release
* commit that version as well
* checkout the original branch
* merge release branch to the original branch

All of that can be done via one of: 
* `mvn ru.mihkopylov:versioner-maven-plugin:release -Doperation=minor`
* `mvn versioner:release -Doperation=minor`
* `mvn versioner:release`, and when `maven` requests a `type` parameter, type `minor`.

## Configuration

### Actors

Each small action is executed by Actor. 
There's a number of provided Actors.

#### Git Actors

* branch
  * input: version
  * description: create new branch using template `release-%MAJOR%.%MINOR%`
  * output: created branch name
* checkout:
  * input: branch name
  * description: checkout the branch
  * output: branch name
* commit:
  * input: commit message
  * description: commit current changes using commit message
  * output: null
* merge:
  * input: branch name to merge
  * description: merges provided branch into the current one
  * output: null
* tag:
  * input: tag name
  * description: set tag to current commit
  * output: tag name
* getCurrentBranch:
  * input: null
  * description: get current branch name
  * output: current branch name

#### Version Actors

* getCurrentVersion:
  * input: null
  * description: get current version from pom.xml
  * output: current version
* setVersion:
  * input: version
  * description: sets provided version to `pom.xml`
  * output: version
* increaseMajorVersion:
  * input: version
  * description: increase major part of the version
  * output: increased version
* increaseMinorVersion:
  * input: version
  * description: increase minor part of the version
  * output: increased version
* increasePatchVersion:
  * input: version
  * description: increase patch part of the version
  * output: increased version
* rc1Version:
  * input: version
  * description: add `-RC1` suffix to version
  * output: updated version
* rc2Version:
  * input: version
  * description: add `-RC2` suffix to version
  * output: updated version
* snapshotVersion:
  * input: version
  * description: add `-SNAPSHOT` suffix to version
  * output: updated version
* releaseVersion:
  * input: version
  * description: removes all suffixes from version
  * output: updated version

### Operations

List of Actors form an Operation. 
There're some provided Operations: 

* major
* minor
* patch

There's also ability to configure custom operations or override the provided ones.

In order to configure a custom operation add a `plugin/configuration/operations/operation` block to your `pom.xml`.

Operation contains of `name` and `actions`, which is a list of `action` blocks. 
Each `action` has:
* `actor` (required) which contains name of Actor
* `input` (optional) which defines a name of context variable that will be passed as input to the Actor
* `output` (optional) which defines a name of context variable that will keep the result of Actor execution.

#### Example
Here's default `major` operation configuration:

```xml
<plugin>
    <groupId>ru.mihkopylov</groupId>
    <artifactId>versioner-maven-plugin</artifactId>
    <version>LATEST</version>
    <configuration>
        <operations>
            <operation>
                <name>major</name>
                <actions>
                    <action>
                        <actor>getCurrentBranch</actor>
                        <output>originBranch</output>
                    </action>
                    <action>
                        <actor>getCurrentVersion</actor>
                        <output>currentVersion</output>
                    </action>
                    <action>
                        <actor>increaseMajorVersion</actor>
                        <input>currentVersion</input>
                        <output>releaseVersion</output>
                    </action>
                    <action>
                        <actor>releaseVersion</actor>
                        <input>releaseVersion</input>
                        <output>releaseVersion</output>
                    </action>
                    <action>
                        <actor>branch</actor>
                        <input>releaseVersion</input>
                        <output>releaseBranch</output>
                    </action>
                    <action>
                        <actor>checkout</actor>
                        <input>releaseBranch</input>
                    </action>
                    <action>
                        <actor>setVersion</actor>
                        <input>releaseVersion</input>
                    </action>
                    <action>
                        <actor>commit</actor>
                        <input>releaseVersion</input>
                    </action>
                    <action>
                        <actor>tag</actor>
                        <input>releaseVersion</input>
                    </action>
                    <action>
                        <actor>increasePatchVersion</actor>
                        <input>releaseVersion</input>
                        <output>nextVersion</output>
                    </action>
                    <action>
                        <actor>snapshotVersion</actor>
                        <input>nextVersion</input>
                        <output>nextVersion</output>
                    </action>
                    <action>
                        <actor>setVersion</actor>
                        <input>nextVersion</input>
                    </action>
                    <action>
                        <actor>commit</actor>
                        <input>nextVersion</input>
                    </action>
                    <action>
                        <actor>checkout</actor>
                        <input>originBranch</input>
                    </action>
                    <action>
                        <actor>merge</actor>
                        <input>releaseBranch</input>
                    </action>
                </actions>
            </operation>
        </operations>
    </configuration>
</plugin>
```

### Custom Actors

In order to provide even more flexibility, the plugin supports custom Actors that can be defined using [Jython](https://www.jython.org/) language, that a Java implementation of Python.

To add a custom Actor definition need to add a `plugin/configuration/extraActors/extraActor` block which has:
* name - the name of the Actor which it can be referred by in Operation definition
* code - the source code of the Actor. 

Actor name may be unique, or may be equal to one of provided ones - in this case the new Actor will replace the provided one.
Code should have a class named `Actor` that implements `ru.mihkopylov.actor.Actor` interface with a single method `public String act(String input);`

Note, that Jython is sensitive to indentations, same as Python. 

Here's an example of such a definition that always returns a `0.0.0-SNAPSHOT` version.

```xml
<plugin>
    <groupId>ru.mihkopylov</groupId>
    <artifactId>versioner-maven-plugin</artifactId>
    <version>LATEST</version>
    <configuration>
         <extraActors>
             <extraActor>
                 <name>increasePatchVersion</name>
                 <code>from ru.mihkopylov.actor import Actor as ActorInterface
 class Actor(ActorInterface):
     def act(self, input):
         return "0.0.0-SNAPSHOT"
                 </code>
             </extraActor>
         </extraActors>
    </configuration>
</plugin>
```