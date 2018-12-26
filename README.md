# Versioner Maven Plugin

[![Build Status](https://travis-ci.com/mih-kopylov/versioner-maven-plugin.svg)](https://travis-ci.com/mih-kopylov/versioner-maven-plugin)

## Usage
Assume the current project version is `1.2.3-SNAPSHOT`

* `ru.omickron:versioner-maven-plugin:release -Doperation=PATCH`
  * change version to `1.2.3`
  * commit
  * set a tag to the commit
  * change version to `1.2.4-SNAPSHOT`
  * commit
* `versioner:release -Doperation=PATCH` - the same as above
* `versioner:release` - the same as above, but requests `type` parameter. **This is a preferable usage scenario**
* `versioner:release -Doperation=MINOR`
  * create a branch `release-1.3`
  * change version to `1.3.0`
  * commit 
  * set a tag to the commit
  * change version to `1.3.1-SNAPSHOT`
  * commit
  * checkout the original branch
  * merge `release-1.3` branch 
* `versioner:release -Doperation=MAJOR`
  * create a branch `release-2.0`
  * change version to `2.0.0`
  * commit 
  * set a tag to the commit
  * change version to `2.0.1-SNAPSHOT`
  * commit 
  * checkout the original branch
  * merge `release-2.0` branch 

## Configuration
Plugin supports custom operations configured in `pom.xml`. By default it has 3 operations:

* major
* minor
* patch

All operation and actor names are case-insensitive.

It's possible to override these operations or add new ones in `pom.xml`. 

### Actors

* branch
  * input: version
  * action: create new branch
  * output: branch name
* checkout:
  * input: branch name
  * action: checkout the branch
  * output: branch name
* commit:
  * input: commit message
  * action: commit current changes
  * output: null
* getCurrentBranch:
  * input: null
  * action: get current branch name
  * output: current branch name
* getCurrentVersion:
  * input: null
  * action: get current version from pom.xml
  * output: current version
* increaseMajorVersion:
  * input: version
  * action: increase major part of the version
  * output: updated version
* increaseMinorVersion:
  * input: version
  * action: increase minor part of the version
  * output: updated version
* increasePatchVersion:
  * input: version
  * action: increase patch part of the version
  * output: updated version
* merge:
  * input: branch name to merge
  * action: merges provided branch into the current one
  * output: null
* rc1Version:
  * input: version
  * action: add `-RC1` suffix to version
  * output: updated version
* rc2Version:
  * input: version
  * action: add `-RC2` suffix to version
  * output: updated version
* releaseVersion:
  * input: version
  * action: removes all suffixes from version
  * output: updated version
* setVersion:
  * input: version
  * action: sets current version to `pom.xml`
  * output: version
* snapshotVersion:
  * input: version
  * action: add `-SNAPSHOT` suffix to version
  * output: updated version
* tag:
  * input: tag name
  * action: set tag to current commit
  * output: tag name

### Example
Here's default `major` operation configuration:

```xml
<plugin>
    <groupId>ru.omickron</groupId>
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
