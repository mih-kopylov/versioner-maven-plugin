# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.3.0...HEAD)

## [3.3.1](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.3.0...3.3.1) - 2020.07.04
### Fixed
- If several custom actors are defined with the same name then last actor wins

## [3.3.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.2.1...3.3.0) - 2020.07.04
### Added
- Ability to configure release branch prefix
- Ability to create a branch from any provided name

### Deprecated
- `versioner:release` mojo is deprecated in favor of `versioner:run`

## [3.2.1](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.2.0...3.2.1) - 2019.11.21
### Fixed
- Fix complex version suffixes separated with hyphens and dots according to https://semver.org/#spec-item-9

## [3.2.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.1.1...3.2.0) - 2019.11.13
### Added
- Groovy scripting language

## [3.1.1](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.1.0...3.1.1) - 2019.10.06
### Changed
- CommitActor commits now not only `pom.xml` files, but all changed files

## [3.1.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/3.0.0...3.1.0) - 2019.10.05
### Added
- Jython integration to support scripting in custom actors

## [3.0.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/2.1.0...3.0.0) - 2019.08.10
### Added
- Sources are moved to `ru.mihkopylov` package

## [2.1.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/2.0.0...2.1.0) - 2018.12.26
### Added
- `RC1` and `RC2` support

## [2.0.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/1.1.2...2.0.0) - 2018.12.24
### Added
- Flexible plugin configuration using Actors and Operations

## [1.1.2](https://github.com/mih-kopylov/versioner-maven-plugin/compare/1.1.1...1.1.2) - 2018.12.16
### Fixed
- Set autoReleaseAfterClose to true

## [1.1.1](https://github.com/mih-kopylov/versioner-maven-plugin/compare/1.1.0...1.1.1) - 2018.12.16
### Added 
- plugin publication to https://search.maven.org

## [1.1.0](https://github.com/mih-kopylov/versioner-maven-plugin/compare/1.0.0...1.1.0) - 2018.10.29
### Added
- prompter to choose operation

## [1.0.0](https://github.com/mih-kopylov/versioner-maven-plugin/releases/tag/1.0.0) - 2018-09-04
### Added
- Initial release of base functionality