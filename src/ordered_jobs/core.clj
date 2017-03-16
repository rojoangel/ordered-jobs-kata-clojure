(ns ordered-jobs.core
  (:require [clojure.string :as str]))

(defrecord Dependency [from to])

(defn line->dependency [line]
  (let [[from to] (str/split line #"=>")]
    (if to
      (->Dependency (str/trim from) (str/trim to))
      (->Dependency (str/trim from) to))))

(defn- parse-jobs [jobs]
  (let [lines (str/split-lines jobs)]
    (map line->dependency lines)))

(defn sequence-jobs [jobs]
  (if (empty? jobs)
    jobs
    (str/join (map :from (parse-jobs jobs)))))
