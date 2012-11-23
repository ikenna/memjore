(ns ^{:doc "functions to pull data from the db"}
    memjore.models.db
    (:require [monger core collection])
    (:use [memjore.models.validation]))

(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [id]
  (first
   (monger.collection/find-maps
    "members" {:_id (org.bson.types.ObjectId. id)})))

(defn add-member [m]
  (if (true? (is-valid m))
    (monger.collection/insert-and-return "members" m)
    {:message "Error persisting member" :success false}))


(defn update [id member]
  (try
    (do
      (monger.collection/update "members" {:_id (org.bson.types.ObjectId. id)} member)
      {:message "Successful update" :success true})
    (catch Exception e
      {:message (.getMessage e) :success false })))

(defn edit-member [id updated-member-data]
  (if (true? (is-valid updated-member-data))
    (update id updated-member-data)
    (hash-map :message "Cannot save - member invalid" :success false)))

(defn members []
  (monger.collection/find-maps "members"))