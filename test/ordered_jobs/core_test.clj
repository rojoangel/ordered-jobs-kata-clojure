(ns ordered-jobs.core-test
  (:use midje.sweet)
  (:use [ordered-jobs.core]))

(def no-job "")

(facts "about ordered jobs"
       (fact "if no jobs the output is an empty sequence of jobs"
             (order no-job) => ""))
