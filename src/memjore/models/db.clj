(ns ^{:doc "functions to pull data from the db"}
    memjore.model.db
    (:require [monger core collection]))


(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [n]
  (first (monger.collection/find-maps "members" {:id n})))

(defn add-member [n]
  (monger.collection/insert "members" input ))

(defn members []
  (monger.collection/find-maps "members" ))