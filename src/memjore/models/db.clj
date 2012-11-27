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
    (try
      (do
        (monger.collection/insert-and-return "members" m))
;;        {:message "Succesffully added member" :success true})
      (catch Exception e
        {:message (.getMessage e) :success false}))
    {:message "Error: Invalid member" :success false}))


(defn update [id member]
  (try
    (do
      (monger.collection/update "members" {:_id (org.bson.types.ObjectId. id)} member)
      (get-member id))
    ;;  {:message "Successful update" :success true})
    (catch Exception e
      {:message (.getMessage e) :success false })))

(defn edit-member [id updated-member-data]
  (if (true? (is-valid updated-member-data))
    (update id updated-member-data)
    (hash-map :message "Cannot save - member invalid" :success false)))

(defn members []
  ;; Look for a way to sort the result according to first name
  (monger.collection/find-maps "members"))