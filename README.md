stexfires - structured text file record stream
===============================================
A stream-based Java 8 library to work with data from structured text files (CSV, Fixed width, INI, Properties, JSON, XML, ...).

The library is still under development.


Goals
-----
- expandable, adaptable, flexible
- stable and ready for production
- well tested
- elegant, short and fluent
- easy and simple to use


Motivation
----------
- Learning and using new "Java 8" techniques and functionalities
  - Functional Interfaces
  - Lambda Expressions
  - Streams
  - New classes and packages (Optional, Objects, java.time, ...)
  - Default methods
- Building a useful, free and open Java library (JAR)


Functionalities
---------------
- convert / transform (ETL)
  - **Input**: One or more files and their associated file specifications
  - **Output**: One or more files with other file specifications
  - split, merge, sort, filter, convert, pivot, ...
- generate
  - **Input**: File specification
  - File with random or rule-based values
  - Code
  - Documentation
- analyse
  - **Input**: File and associated file specification
  - Search a record or a field value
  - Statistical evaluations and calculations
  - Validate the content according the specification
- compare
  - **Input**: Two or more files and their associated file specifications
  - *It is unclear whether this is feasible and meaningful.*


File formats
------------
- Delimiter-separated values (CSV, TSV, ...)
- Fixed width
- Configuration files (INI, INF, CFG, URL, ...)
- Java properties (java.util.Properties and java.util.PropertyResourceBundle)
- JSON
- XML
- Optional
  - YAML
  - TOML
  - HTML DOM
  - Log files
  - Literature
- Ouput only
  - HTML
  - Markdown


License
-------
*stexfires* is licensed under the [MIT License].


Usage
-----
*stexfires* requires only [Java 8][Java 8].


Building/Developing
-------------------
*stexfires* uses a [Gradle][Gradle]-based build system and provides a [wrapper][Gradle Wrapper].
It is a script which is called from the root of the source tree. It downloads and installs [Gradle][Gradle] automatically.
Depending on the system it may be necessary to call "./gradlew" instead of "gradlew".


Versioning
----------
*stexfires* uses "[Semantic Versioning][SemVer]" for versioning.


Contributing
------------
Please use [issues on GitHub][GitHub Issues] for contact, questions, feature suggestions, contributions and bug reports.

We welcome contributions, especially through [pull requests on GitHub][GitHub Pull requests].
Submissions must be licensed under the [MIT License].


Contributors
------------
[Overview of contributors][GitHub Contributors]

* Mathias Kalb [@mkalb](https://github.com/mkalb)


[MIT License]: https://github.com/stexfires/stexfires/raw/master/LICENSE "MIT License"
[Java 8]: https://www.java.com "Java 8"
[Gradle]: http://gradle.org "Gradle"
[Gradle Wrapper]: http://gradle.org/docs/current/userguide/gradle_wrapper.html "Gradle Wrapper" 
[SemVer]: http://semver.org/ "SemVer"
[GitHub Issues]: https://github.com/stexfires/stexfires/issues/ "stexfires issues"
[GitHub Contributors]: https://github.com/stexfires/stexfires/graphs/contributors/ "stexfires contributors"
[GitHub Pull requests]: http://help.github.com/send-pull-requests "send pull request"
