Clojure script processor for Spring Surf
========================================

Spring Surf is a view composition framework for Spring MVC that plugs into your existing Spring applications.
It provides a scriptable and content-centric approach to building web applications.

This extension provides a Clojure script processor that makes it possible to implement Clojure controllers
for your Spring Surf web scripts.

================================================

Usage
=====

In order to bootstrap the Clojure script processor you might just `<import resource="classpath:org/springframework/extensions/clj/webscripts/clojure-webscripts-context.xml">` in your application context.

Your clojure web scripts must return an instance of a concrete WebScript implementation. Its `run` method will be executed by the processor and the result passed to the view processor.
Look at the [test webscripts](https://github.com/skuro/spring-webscripts-addon-clojure/tree/master/src/test/resources/webscripts/test) if your're looking for an example or a starting point.
