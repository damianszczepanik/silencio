[![Github build](https://github.com/damianszczepanik/silencio/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/damianszczepanik/silencio/actions/workflows/build.yml)
[![AppVeyor Status](https://img.shields.io/appveyor/ci/damianszczepanik/silencio/master.svg?label=AppVeyor%20build)](https://ci.appveyor.com/project/damianszczepanik/silencio/history)

[![Coverage Status](https://codecov.io/gh/damianszczepanik/silencio/branch/master/graph/badge.svg)](https://codecov.io/github/damianszczepanik/silencio)
[![Sonarqube Status](https://sonarcloud.io/api/project_badges/measure?project=damianszczepanik_silencio&metric=alert_status)](https://sonarcloud.io/dashboard?id=damianszczepanik_silencio)
[![Codacy](https://api.codacy.com/project/badge/grade/7d8811903fda44a39bb0f7c5e142a965)](https://app.codacy.com/gh/damianszczepanik/silencio/dashboard)
[![Codebeat](https://codebeat.co/badges/d53d81f6-f812-43d0-9fdc-4662ccec1e86)](https://codebeat.co/projects/github-com-damianszczepanik-silencio-master)
[![Vulnerabilities](https://snyk.io/test/github/damianszczepanik/silencio/badge.svg)](https://app.snyk.io/org/damianszczepanik/project/aa636d8e-3d8b-49cd-84d5-5a5aa1e845fd)

[![Maven Central](https://img.shields.io/maven-central/v/pl.damianszczepanik/silencio.svg)](http://search.maven.org/#search|gav|1|g%3A%22pl.damianszczepanik%22%20AND%20a%3A%22silencio%22)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/)

# silencio

Silencio is a Java library for transforming and converting formats such as
- [XML](https://pl.wikipedia.org/wiki/XML)
- [JSON](https://pl.wikipedia.org/wiki/JSON)
- [Properties](https://en.wikipedia.org/wiki/.properties)
- [YAML](https://pl.wikipedia.org/wiki/YAML)

using well defined [API](https://github.com/damianszczepanik/silencio/tree/master/src/main/java/pl/szczepanik/silencio/api). It is applicable for most of operations such as:
- [obfuscation](https://en.wikipedia.org/wiki/Obfuscation_%28software%29)
- minimisation (eg [anonymization](https://en.wikipedia.org/wiki/Data_anonymization), minifying)
- transformation (eg [encryption](https://en.wikipedia.org/wiki/Encryption))

It is built from [processors](src/main/java/pl/szczepanik/silencio/api/Processor.java) that manage transformations of the files (XML, JSON, Properties, etc.) [decisions](src/main/java/pl/szczepanik/silencio/api/Decision.java) which decide which elements should be converted and [converters](src/main/java/pl/szczepanik/silencio/api/Converter.java) that changes old value into new one.

## Examples

#### Make it fast, write it simple

As presented in [tests](src/test/java/pl/szczepanik/silencio/integration/JSONProcessorIntegrationTest.java) this is quite easy to convert the file:

```java
   Reader input = new FileReader("myStructure.json");
   Writer output = new StringWriter();

   Processor processor = new Builder(Format.JSON).with(Builder.NUMBER_SEQUENCE).build();
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

#### Sky is the limit

So you know how it works but you want to decide which nodes should be transfered into what values. Imagine you are renting cars and your partner asked you to share all your specifications. Sounds good but you don't want to share prices (sensitive information). Also your database is a little bit outdated because all your cars already have sunroof even your services provide different information.

Take a look at [example](src/test/java/pl/szczepanik/silencio/integration/BuilderIntegrationTest.java) below. There are two iterations on the same file. First removes all values that match to given key `money|cash|price`. Second validates key (`sunroot`) and value (`Optional`) and transfers values into new one (`[Standard]`).

```java
   Builder builder = new Builder(Format.PROPERTIES);
   builder.with(new MatcherDecision(".*(money|cash|price).*", null), Builder.BLANK)
          .with(new MatcherDecision(".*sunroof.*", ".*Optional.*"), new StringConverter("[Standard]"));
   Processor processor = builder.build();
   processor.load(input);
   processor.process();
   processor.write(output);

```
and the conversion will produce following outcome:

<pre>
|   suv.properties  -->>         |   [ removing price ]           |   [ updated sunroof ]          |
|================================|================================|================================|
| model=SUV                      | model=SUV                      | model=SUV                      |
| price.value=38504              | price.value=                   | price.value=                   |
| price.currency=$               | price.currency=                | price.currency=                |
| wheels=4                       | wheels=4                       | wheels=4                       |
| size.length=4.45               | size.length=4.45               | size.length=4.45               |
| size.height=2.05               | size.height=2.05               | size.height=2.05               |
| size.width=2.200               | size.width=2.200               | size.width=2.200               |
| weight.value=2.200             | weight.value=2.200             | weight.value=2.200             |
| weight.unit=ton                | weight.unit=ton                | weight.unit=ton                |
| accessories.sunroof=[Optional] | accessories.sunroof=[Optional] | accessories.sunroof=[Standard] |
| accessories.radio=yes          | accessories.radio=yes          | accessories.radio=yes          |
| fuel.diesel=true               | fuel.diesel=true               | fuel.diesel=true               |
| fuel.LPG=true                  | fuel.LPG=true                  | fuel.LPG=true                  |
| notes=null                     | notes=null                     | notes=null                     |
| alarms=                        | alarms=                        | alarms=                        |
| assistance=                    | assistance=                    | assistance=                    |
|================================|================================|================================|
</pre>

## Usage

Add a maven dependency (using version from above shield) to your `pom.xml` or `build.gradle`:
```xml
<dependency>
    <groupId>pl.damianszczepanik</groupId>
    <artifactId>silencio</artifactId>
    <version>2.0.0</version> <!-- check above shield to confirm last release -->
</dependency>
```

The simplest way to understand how to use processor with set of converters is to check [tests](src/test/java/pl/szczepanik/silencio/integration) that validate this implementation.

## Create custom processor, converter or format

Both processors and converters can be extended. They are like plugins: you can add your own implementation as long as you keep the contract. There are several [built-in](src/main/java/pl/szczepanik/silencio/converters) converters that can be easily accessed via [builder](src/main/java/pl/szczepanik/silencio/core/Builder.java). Nevertheless if you need to create your own just implement [``convert()``](src/main/java/pl/szczepanik/silencio/api/Converter.java#L19) method and provide the algorithm you expect. Sometimes rules might be very [simple](src/main/java/pl/szczepanik/silencio/converters/BlankConverter.java).

Silencio supports most of the popular data formats but it is possible to write support for new one. It is not difficult when using external library that allows to manipulate such format. For more reference check [pull](https://github.com/damianszczepanik/silencio/pull/70) that introduced support for XML.

## Code quality, test coverage

Once you developed your new processor or converter you should test it by providing several unit tests and by passing it to [processor checker](src/main/java/pl/szczepanik/silencio/diagnostics/ProcessorSmokeChecker.java) that makes basic tests using [edge cases](https://en.wikipedia.org/wiki/Edge_case).

![codecov.io](https://codecov.io/gh/damianszczepanik/silencio/branch/master/graphs/tree.svg)

## Contribution

When you implement new processor or converter you are more than welcome to [pull](https://github.com/damianszczepanik/silencio/pulls) this change and make someone happier!

Mind that this project has several metrics that measure code quality. From [continuous integration](https://travis-ci.org/damianszczepanik/silencio) and [code coverage](https://codecov.io/github/damianszczepanik/silencio) to [coverity](https://scan.coverity.com/projects/damianszczepanik-silencio) and [checkstyle](http://checkstyle.sourceforge.net). All PRs are welcome but try to keep high code coverage and good documentation of the code you deliver.

