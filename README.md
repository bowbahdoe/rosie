## What is this

In the Clojure ecosystem, every http server works off of a specification called [ring](https://github.com/ring-clojure/ring).

The ring spec says how http requests and responses should be translated into a clojure map, what keys
are required, and what those keys mean.

This is similar. There are `Request` and `Response` classes which should be easy to construct from any http
server implementation. 

You can think of it as an attempt to do something similar to `jakarta.servlet`.



