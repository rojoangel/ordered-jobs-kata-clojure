(ns ordered-jobs.core-test
  (:use midje.sweet)
  (:use [ordered-jobs.core]))

(def no-job "")

(def single-job "a =>")

(def multiple-jobs "a =>\nb =>\nc =>")

(facts "about ordered jobs"
       (fact "if no jobs are given the output is an empty sequence of jobs"
             (sequence-jobs no-job) => "")
       (fact "if a single job is given the output is a single job"
             (sequence-jobs single-job) => "a")
       (fact "if multiple jobs are given the output sequence contains all jobs in no particular order"
             (sequence-jobs multiple-jobs) => "abc"))
