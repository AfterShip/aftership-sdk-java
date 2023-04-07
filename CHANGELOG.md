# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [2.1.8] - 2023-04-07
### Fixed
- Fix No option constructor throws NullPointerException, add null options validate
  - PR: https://github.com/AfterShip/aftership-sdk-java/pull/48
  - Issue: https://github.com/AfterShip/aftership-sdk-java/issues/47
- Fix retry issue (https://github.com/AfterShip/aftership-sdk-java/pull/50)

## [2.1.7] - 2023-04-06
### Added
- Add retry feature(https://github.com/AfterShip/aftership-sdk-java/pull/45)

## [2.1.6] - 2022-08-03
### Added
- Add AES signature(https://github.com/AfterShip/aftership-sdk-java/pull/42)

## [2.1.5] - 2022-07-20
### Added
- Add predict-batch endpoint and update tracking fields https://github.com/AfterShip/aftership-sdk-java/pull/37
### Changed
- Update tracking fields https://github.com/AfterShip/aftership-sdk-java/pull/36

## 2.1.4
- Add some fields of tracking object. [tracking object](https://developers.aftership.com/reference/object-tracking)
- Add more request parameters for get trackings interface.

## [2.1.3] 2022-03-22
### Fixed
- fix: mask aftership api key in exception message

## [2.1.2] - 2022-01-26
### Fixed
- fix: change shipmentWeight from Integer to Float #26

## 2.1.1
### Changed
- update README and build.gradle

## [2.1.0] - 2021-11-17
### Changed
- Add some fields of tracking object. [tracking object](https://developers.aftership.com/reference/object-tracking)
- Add more request parameters for get trackings interface. 

### [2.0.8] - 2021-03-08
- Fixed
 - [API response failed to throw IllegalStateException](https://github.com/AfterShip/aftership-sdk-java/issues/18)

## 2.0.0-alpha - 2020-05-28
### Added
- Support latest features in v4 API
- Use Gradle as a dependency manager
- Published in maven central repository
- Error handling
- Add mock test
- Add samples

Compatibility
- JDK >= 1.8
	
## [1.2.0] - 2016-04-26
### Changed
- Properties added in Checkpoint class
    - slug
    - location

## [1.1.1] - 2016-02-02
### Changed
- Solving issue at Checkpoint.java, typo in ```country_iso3```


[2.1.8]: https://github.com/AfterShip/aftership-sdk-java/compare/2.1.7...2.1.8
[2.1.7]: https://github.com/AfterShip/aftership-sdk-java/compare/2.1.6...2.1.7
[2.1.6]: https://github.com/AfterShip/aftership-sdk-java/compare/2.1.5...2.1.6
[2.1.5]: https://github.com/AfterShip/aftership-sdk-java/compare/v2.1.3...2.1.5
[2.1.3]: https://github.com/AfterShip/aftership-sdk-java/compare/v2.1.2...v2.1.3
[2.1.2]: https://github.com/AfterShip/aftership-sdk-java/compare/v2.1.0...v2.1.2
[2.1.0]: https://github.com/AfterShip/aftership-sdk-java/compare/v2.0.8...v2.1.0
[2.0.8]: https://github.com/AfterShip/aftership-sdk-java/compare/v1.2.0...v2.0.8
[1.2.0]: https://github.com/AfterShip/aftership-sdk-java/compare/1.1.1...v1.2.0
[1.1.1]: https://github.com/AfterShip/aftership-sdk-java/releases/tag/1.1.1
