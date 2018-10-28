# Versioner Maven Plugin

[![Build Status](https://travis-ci.com/mih-kopylov/versioner-maven-plugin.svg)](https://travis-ci.com/mih-kopylov/versioner-maven-plugin)

## Usage
Assume the current project version is `1.2.3-SNAPSHOT`

* `ru.omickron:versioner-maven-plugin:release -Dtype=PATCH`
  * change version to `1.2.3`
  * commit
  * set a tag to the commit
  * change version to `1.2.4-SNAPSHOT`
  * commit
* `versioner:release -Dtype=PATCH` - the same as above
* `versioner:release` - the same as above, but requests `type` parameter. **This is a preferable usage scenario**
* `versioner:release -Dtype=MINOR`
  * create a branch `release-1.3`
  * change version to `1.3.0`
  * commit 
  * set a tag to the commit
  * change version to `1.3.1-SNAPSHOT`
  * commit
  * checkout the original branch
  * merge `release-1.3` branch 
* `versioner:release -Dtype=MAJOR`
  * create a branch `release-2.0`
  * change version to `2.0.0`
  * commit 
  * set a tag to the commit
  * change version to `2.0.1-SNAPSHOT`
  * commit 
  * checkout the original branch
  * merge `release-2.0` branch 
