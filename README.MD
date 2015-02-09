A project to explore design patterns. Each pattern may contain one module and usually an other usage module.

###  Build status:  
[![Build Status](https://travis-ci.org/venkateshamurthy/design-patterns.svg?branch=master)](https://travis-ci.org/venkateshamurthy/design-patterns)  
[![Coverage Status](https://coveralls.io/repos/venkateshamurthy/design-patterns/badge.png?branch=master)](https://coveralls.io/r/venkateshamurthy/design-patterns?branch=master)

Note:
-----
This project may utilize other open source libraries to fit the gaps and acknowledges those authors for using it. 

## Creational Patterns:

### Builder

The Builder pattern examples here show case some of the open source library specific way in which the builder could be used. While there are many
open source libraries that provide this function; the author has been experimenting few of these to see which library could be used in what situation.
One of the main variants of the use case is about generating a builder for an already generated POJO while the other usual case is 
about creating a new POJO with an intent to help generate a builder...

Please use builders and builders-usage module to auto-generate a builder in case POJOs are generated else where otherwise when 
the developer has a choice of creating POJO, one has a choice to make  such as Google autovalue, pojobuilder but nothing comes quite 
close to the lean, mean lombok.(but there are always caveats to consider)...

Please refer to a [detail readme](./builders-README.MD)

