(ns ^{:doc "functions to pull data from the db"}
    memjore.models.db
    (:require [monger core collection]))


(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [id]
  (first
   (monger.collection/find-maps
    "members" {:_id (org.bson.types.ObjectId. id)})))

(defn add-member [m]
  (monger.collection/insert "members" m))

(defn members []
  (monger.collection/find-maps "members" ))