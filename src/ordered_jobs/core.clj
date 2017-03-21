(ns ordered-jobs.core
  (:require [clojure.string :as str]))

(defrecord Dependency [from to])

(defn by-dependency [x y]
  (if (= (:to y) (:from x))
    -1
    (if (= (:to x) (:from y))
      1
      (if (and (nil? (:to x)) (not (nil? (:to y))))
        -1
        (if (and (nil? (:to y)) (not (nil? (:to x))))
          1
          (compare (:from x) (:from y)))))))

(defn line->dependency [line]
  (let [[from to] (str/split line #"=>")]
    (if to
      (->Dependency (str/trim from) (str/trim to))
      (->Dependency (str/trim from) to))))

(defn- parse-jobs [jobs]
  (let [lines (str/split-lines jobs)]
    (map line->dependency lines)))


(defn sequence-jobs [jobs]
  (let [dependencies (parse-jobs jobs)]
    (if (some #(= (:to %) (:from %)) dependencies)
      (throw
        (ex-info "Circular Reference"
                 {:error-type :circular-reference}))
      (str/join (map :from (sort by-dependency dependencies))))))
