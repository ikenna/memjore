(ns ^{:doc "functions to pull data from the db"}
    memjore.models.db
    (:require [monger core collection]))


(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [n]
  (first (monger.collection/find-maps "members" {:id n})))

(defn add-member [m]
  (monger.collection/insert "members" m))

(defn members []
  (monger.collection/find-maps "members" ))