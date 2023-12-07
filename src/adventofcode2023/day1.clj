(ns adventofcode2023.day1
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def input (io/resource "day1-input.txt"))

(def simple-digit-finder-regex (re-pattern #"\d"))

(def translation {"one"   "1"
                  "two"   "2"
                  "three" "3"
                  "four"  "4"
                  "five"  "5"
                  "six"   "6"
                  "seven" "7"
                  "eight" "8"
                  "nine"  "9"})

(def translated-digits-regex
  "This uses positive a lookeahead to allow things like '7oneight' matching as '718' instead of '71ight'.
   The only caveat is that matches are of the form (7 7), (o one), and (e eight), for the example above,
   so I always look at the second capture group to know what is the actual matched word/digit"
  (re-pattern (str "(?=(\\d|"
                   (string/join "|" (keys translation))
                   "))\\w")))

(defn digits
  [line]
  (re-seq simple-digit-finder-regex line))

(defn calibration-number
  [digits]
  (Integer/parseInt (str (first digits) (last digits))))

(defn part-1
  []
  (->> input
       slurp
       string/split-lines
       (map digits)
       (map calibration-number)
       (reduce +)))

(defn digits-with-translation
  [line]
  (->> line
       (re-seq translated-digits-regex)
       (map second)
       (map #(or (get translation %) %))))

(defn part-2
  []
  (->> input
       (slurp)
       (string/split-lines)
       (map digits-with-translation)
       (map calibration-number)
       (reduce +)))

(part-1)
;; => 54159

(part-2)
;; => 53866
