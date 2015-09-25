[![Build Status](https://img.shields.io/travis/damianszczepanik/silencio/master.svg)](https://travis-ci.org/damianszczepanik/silencio)
[![Coverage Status](https://img.shields.io/codecov/c/github/damianszczepanik/silencio/master.svg)](https://codecov.io/github/damianszczepanik/silencio)
[![Coverity Status](https://scan.coverity.com/projects/6162/badge.svg)](https://scan.coverity.com/projects/damianszczepanik-silencio)
[![Maven Central](https://img.shields.io/maven-central/v/pl.damianszczepanik/silencio.svg)](http://search.maven.org/#search|gav|1|g%3A%22pl.damianszczepanik%22%20AND%20a%3A%22silencio%22)
[![Maven Dependencies](https://www.versioneye.com/user/projects/55c5300965376200170035e9/badge.svg)](https://www.versioneye.com/user/projects/55c5300965376200170035e9?child=summary)

# silencio

Silencio is a library for transforming and converting any format such as [JSON](https://pl.wikipedia.org/wiki/JSON) or [Properties](https://en.wikipedia.org/wiki/.properties) files. It is applicable for most of operations such as:
- obfuscation
- minimisation (eg anonymous)
- transformation

It is built from [processors](src/main/java/pl/szczepanik/silencio/api/Processor.java) that know how transform given file format (JSON, Properties, etc.) and [converters](src/main/java/pl/szczepanik/silencio/api/Converter.java) that are responsible for transforming existing values into new one.

## Example

As presented in [tests](src/test/java/pl/szczepanik/silencio/integration/JSONProcessorTestInt.java) there is quite easy to convert the file:

```java
   Reader input = new FileReader("myStructure.json");
   Writer output = new StringWriter();

   Processor processor = ConverterBuilder.build(Format.JSON, ConverterBuilder.NUMBER_SEQUENCE);
   processor.load(input);
   processor.process();
   processor.write(output);
   
   System.out.println(output);
   
```
and as the result you may expect (depends of [converter](src/main/java/pl/szczepanik/silencio/converters) you selected) following output:

<pre>
|   suv.json  -->>                         | < BlankConverter >            | < NumberSequenceConverter > | < YourConverter>  |
|==========================================|===============================|=============================|===================|
| {                                        | {                             | {                           |                   |
|  "model" : "SUV",                        |   "model" : "",               |   "model" : 1,              |                   |
|  "wheels" : 4,                           |   "wheels" : "",              |   "wheels" : 2,             |                   |
|  "size" : {                              |   "size" : {                  |   "size" : {                |                   |
|    "length" : 4.45,                      |     "length" : "",            |     "length" : 3,           |  create your      |
|    "height" : 2.05,                      |     "height" : "",            |     "height" : 4,           |                   |
|    "width" : 2.200                       |     "width" : ""              |     "width" : 5             |  own              |
|  },                                      |   },                          |   },                        |    converter      |
|  "weight" : {                            |   "weight" : {                |   "weight" : {              |                   |
|    "value" : 2.200,                      |     "value" : "",             |     "value" : 5,            | that's pretty     |
|    "unit" : "ton"                        |     "unit" : ""               |     "unit" : 6              |                   |
|  },                                      |   },                          |   },                        |                   |
|  "accessories" : [ "sunroof", "radio" ], |   "accessories" : [ "", "" ], |   "accessories" : [ 7, 8 ], |         easy!     |
|  "fuel" : [ {                            |   "fuel" : [ {                |   "fuel" : [ {              |                   |
|    "diesel" : true,                      |     "diesel" : "",            |     "diesel" : 9,           |                   |
|    "LPG" : true                          |     "LPG" : ""                |     "LPG" : 9               |                   |
|  } ],                                    |   } ],                        |   } ],                      |                   |
|  "notes" : null,                         |   "notes" : "",               |   "notes" : 0,              |                   |
|  "alarms" : { },                         |   "alarms" : { },             |   "alarms" : { },           |                   |
|  "assistance" : [ ]                      |   "assistance" : [ ]          |   "assistance" : [ ]        |                   |
| }                                        | }                             | }                           |                   |
|==========================================|===============================|=============================|===================|
</pre>

## Usage

Add a maven dependency (using version from above shield) to your `pom.xml` or `build.gradle`:
```xml
<dependency>
    <groupId>pl.damianszczepanik</groupId>
    <artifactId>silencio</artifactId>
    <version>1.1.0</version>
</dependency>
```

The simplest way to understand how to use processor with set of converters is to check [tests](src/test/java/pl/szczepanik/silencio/integration) that validate this implementation.

## Create custom processor or converter

Both processors and converters can be extended. They are like plugins: you can add your own implementation as long as you keep the contract. There are several [built-in](src/main/java/pl/szczepanik/silencio/converters) converters that can be easily accessed via [builder](src/main/java/pl/szczepanik/silencio/core/ConverterBuilder.java). Nevertheless if you need to create your own just implement [``convert()``](src/main/java/pl/szczepanik/silencio/api/Converter.java) method and provide the algorithm you expect. Sometimes rules might be very [simple](src/main/java/pl/szczepanik/silencio/converters/BlankConverter.java).

## Code quality

Once you developed your new processor or converter you should test it by providing several unit tests and by passing it to [processor checker](src/main/java/pl/szczepanik/silencio/diagnostics/ProcessorSmokeChecker.java) that makes basic tests using [edge cases](https://en.wikipedia.org/wiki/Edge_case).

![codecov.io](http://codecov.io/github/damianszczepanik/silencio/branch.svg?branch=master)

## Contribution
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/)

When you implement new processor or converter you are more than welcome to [pull](https://github.com/damianszczepanik/silencio/pulls) this change and make someone happier!

Mind that this project has several metrics that measure code quality. From [continuous integration](https://travis-ci.org/damianszczepanik/silencio) and [code coverage](https://codecov.io/github/damianszczepanik/silencio) to [coverity](https://scan.coverity.com/projects/damianszczepanik-silencio). Please mind that PR are welcome but try to keep high code coverage.

