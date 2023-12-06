(ns adventofcode2023.day1
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input (io/resource "day1-input.txt"))

(def digit-finder-regex (re-pattern #"\d"))

(defn find-digits
  [line]
  (re-seq digit-finder-regex line))

(defn process-line
  [line]
  (let [digits-in-line (find-digits line)
        first-digit (first digits-in-line)
        last-digit (last digits-in-line)]
    (Integer/parseInt (str first-digit last-digit))))

(defn main []
  (->> input
       (slurp)
       (string/split-lines)
       (map process-line)
       (reduce +)))

(main)
