(ns  memjore.models.ta
  (:use midje.sweet))

(fact "ha"
  (+ 2 2) => 4
  (+ 1 1) => 2
  (+ 2 3) => 5)