(ns adventofcode2023.day2
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input (slurp (io/resource "day2-input.txt")))
(def input-rule {:red 12, :green 13, :blue 14})
(def game-result-regex #"(.+?)(?:;\s|$)")
(def match-number-regex #"^Game (\d+)")


(defn- match-number
  [line]
  (->> line
       (re-find match-number-regex)
       second
       Integer/parseInt))

(defn sets
  [match]
  (->> (string/split match #", ")
       (mapv #(string/split % #" "))
       (map (fn [[number colour]] [(keyword colour) (Integer/parseInt number)]))
       (into {})))

(defn- match-results
  [result]
  (->> result
       (re-seq game-result-regex)
       (map second)
       (map sets)))

(defn game-result
  [line]
  (let [[game results] (string/split line #": ")]
    {:game-number (match-number game)
     :results     (match-results (str results "; "))}))

(defn valid-match?
  [rules match]
  (every? #(and (<= (:red % 0) (:red rules))
                (<= (:green % 0) (:green rules))
                (<= (:blue % 0) (:blue rules)))
          match))

(valid-match? input-rule [{:red 100} {:blue 2} {:green 3}])

(defn- valid-matches
  [rules matches]
  (filter #(valid-match? rules (:results %)) matches))

(reduce + (map :game-number (valid-matches input-rule (map game-result (string/split-lines input)))))
