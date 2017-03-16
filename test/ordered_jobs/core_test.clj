(ns ordered-jobs.core-test
  (:use midje.sweet)
  (:use [ordered-jobs.core]))

(def no-job "")

(def single-job "a =>")

(facts "about ordered jobs"
       (fact "if no jobs are given the output is an empty sequence of jobs"
             (order no-job) => "")
       (fact "if a single job is given the output is a single job"
             (order single-job) => "a"))
