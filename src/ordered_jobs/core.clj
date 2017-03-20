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

(defn insert [dependency dependencies-tree]
  (if (contains? dependencies-tree (:to dependency))
    (assoc (dissoc dependencies-tree (:to dependency)) (:from dependency) (assoc {} (:to dependency) (get dependencies-tree (:to dependency))))
    (if-let [[key val] (some #(and (= (:from dependency) (val %)) %) dependencies-tree)]
      (assoc dependencies-tree key (assoc {} (:from dependency) (:to dependency)))
      (assoc dependencies-tree (:from dependency) (:to dependency)))))

(defn- link-dependencies [dependencies]
  (loop [dependencies dependencies
         dependencies-tree {}]
    (if (empty? dependencies)
      dependencies-tree
      (let [[current & rest] dependencies]
        (recur rest (insert current dependencies-tree))))))

(defn walk-dependencies [dependencies-tree]
  (loop [dependencies-tree-entries (seq dependencies-tree)
         result ""]
    (if (empty? dependencies-tree-entries)
      result
      (let [[current & rest] dependencies-tree-entries]
        (if (empty? (val current))
          (recur rest (concat result (key current)))
          (recur rest (concat result (walk-dependencies (val current)) (key current))))))))

(defn sequence-jobs [jobs]
  (if (empty? jobs)
    jobs
    (str/join (walk-dependencies (link-dependencies (parse-jobs jobs))))))
