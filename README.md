# Ordered Jobs Kata

Here's a [link](http://invalidcast.tumblr.com/post/52980617776/the-ordered-jobs-kata) to the kata description.

The project uses [Midje](https://github.com/marick/Midje/).

## How to run the tests

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.
