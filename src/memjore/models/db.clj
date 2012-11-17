(ns ^{:doc "functions to pull data from the db"}
    memjore.models.db
    (:require [monger core collection]
              [clj-http.client :as client]
              [clojure.data.json :as json])
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

(defn edit-member [id updated-member-data]
  (if (true? (is-valid updated-member-data))
    (monger.collection/update "members" {:_id (org.bson.types.ObjectId. id)} updated-member-data)
  {:message "Error editing member" :success false}))

(defn members []
  (monger.collection/find-maps "members"))

(defn build-url [message mobile]
  (str "https://www.textmagic.com/app/api?username=xxxx&password=yyyy&cmd=send&text=" message "&phone=" mobile "&unicode=0"))

(defn external-text-service [message mobile ]
  (let [url      (build-url message mobile)
        response (client/get url)]
  (:body response)))

(defn send-member-text [message ]
   (vec (map #(external-text-service message (:mobile %)) [(first (members))])))


