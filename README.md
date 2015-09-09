[![Build Status](https://img.shields.io/travis/damianszczepanik/silencio/master.svg)](https://travis-ci.org/damianszczepanik/silencio)
[![Coverage Status](https://img.shields.io/codecov/c/github/damianszczepanik/silencio/master.svg)](https://codecov.io/github/damianszczepanik/silencio)
[![Coverity Status](https://scan.coverity.com/projects/6162/badge.svg)](https://scan.coverity.com/projects/damianszczepanik-silencio)
[![Maven Dependencies](https://www.versioneye.com/user/projects/55c5300965376200170035e9/badge.svg)](https://www.versioneye.com/user/projects/55c5300965376200170035e9?child=summary)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/)

# silencio

Silencio is a library for transforming and converting JSON files. It is applicable for most of operations such as:
- obfuscation
- minimisation
- anonymous

## Philosophy

It is built from [processors](https://github.com/damianszczepanik/silencio/blob/master/src/main/java/pl/szczepanik/silencio/api/Processor.java) that know how transform given file format (XML, JSON, CSV) and [converters](https://github.com/damianszczepanik/silencio/blob/master/src/main/java/pl/szczepanik/silencio/api/Converter.java) that are responsible for transforming existing values into new one.

## Usage

The simplest way to understand how to use chosen processor with set of converters is to check any of the [tests](https://github.com/damianszczepanik/silencio/tree/master/src/test/java/pl/szczepanik/silencio/integration) that validate implementation.

## Custom processor or converter

Both processors and converters can be extended. They are like plugins: you can add your own implementation as long as you keep the contract. There are several [built-in](https://github.com/damianszczepanik/silencio/blob/master/src/main/java/pl/szczepanik/silencio/core/ConverterBuilder.java) converters but if you still need your own then all you need to do is to implement ``convert()`` method and provide the algorithm you need. It might be very [simple](https://github.com/damianszczepanik/silencio/blob/master/src/main/java/pl/szczepanik/silencio/converters/BlankConverter.java).

## Contribution
[![Issues](http://img.shields.io/badge/open%20issues-1-blue.svg)](https://github.com/damianszczepanik/silencio/issues)
[![Pull-Requests](http://img.shields.io/badge/pending%20pull--requests-1-blue.svg)](https://github.com/damianszczepanik/silencio/pulls)

When you implement new processor or converter you are more than welcome to [pull](https://github.com/damianszczepanik/silencio/pulls) this change and make someone happier!
