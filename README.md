[![Build Status](https://img.shields.io/travis/damianszczepanik/silencio/master.svg)](https://travis-ci.org/damianszczepanik/silencio)
[![Coverage Status](https://img.shields.io/codecov/c/github/damianszczepanik/silencio/master.svg)](https://codecov.io/github/damianszczepanik/silencio)
[![Coverity Status](https://scan.coverity.com/projects/6162/badge.svg)](https://scan.coverity.com/projects/damianszczepanik-silencio)
[![Maven Central](https://img.shields.io/maven-central/v/pl.damianszczepanik/silencio.svg)](http://search.maven.org/#search|gav|1|g%3A%22pl.damianszczepanik%22%20AND%20a%3A%22silencio%22)
[![Maven Dependencies](https://www.versioneye.com/user/projects/55c5300965376200170035e9/badge.svg)](https://www.versioneye.com/user/projects/55c5300965376200170035e9?child=summary)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/)

# silencio

Silencio is a library for transforming and converting JSON files. It is applicable for most of operations such as:
- obfuscation
- minimisation
- anonymous

## Philosophy

It is built from [processors](src/main/java/pl/szczepanik/silencio/api/Processor.java) that know how transform given file format (XML, JSON, CSV) and [converters](src/main/java/pl/szczepanik/silencio/api/Converter.java) that are responsible for transforming existing values into new one.

## Usage

Add a maven dependency (using version from above shield) to your pom:
```xml
<dependency>
    <groupId>pl.damianszczepanik</groupId>
    <artifactId>silencio</artifactId>
    <version>1.0.0</version>
</dependency>
```

The simplest way to understand how to use chosen processor with set of converters is to check any of the [tests](src/test/java/pl/szczepanik/silencio/integration) that validate implementation.

## Create custom processor or converter

Both processors and converters can be extended. They are like plugins: you can add your own implementation as long as you keep the contract. There are several [built-in](src/main/java/pl/szczepanik/silencio/converters) that can be easily accessed via [builder](src/main/java/pl/szczepanik/silencio/core/ConverterBuilder.java). Nevertheless if you need to create your own then what you need is to implement [``convert()`` method](src/main/java/pl/szczepanik/silencio/api/Converter.java) and provide the algorithm you expect. Sometimes it can be very [simple](src/main/java/pl/szczepanik/silencio/converters/BlankConverter.java).

Once you developed your new processor you should test it by providing several unit tests and by passing it to [processor tester](src/main/java/pl/szczepanik/silencio/diagnostics/ProcessorSmokeChecker.java) that makes basic tests using [edge cases](https://en.wikipedia.org/wiki/Edge_case).

## Contribution

When you implement new processor or converter you are more than welcome to [pull](https://github.com/damianszczepanik/silencio/pulls) this change and make someone happier!

Mind that this project has several metrics that measure code quality. From [continuous integration](https://travis-ci.org/damianszczepanik/silencio) and [code coverage](https://codecov.io/github/damianszczepanik/silencio) to [coverity](https://scan.coverity.com/projects/damianszczepanik-silencio). Please mind that PR are welcome but try to keep high code coverage.

