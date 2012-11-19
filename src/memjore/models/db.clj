(ns ^{:doc "functions to pull data from the db"}
    memjore.models.db
    (:require [monger core collection]
              [clj-http.client :as client]
              [clojure.data.json :as json])
    (:use [memjore.models.validation]
          [hiccup.util :only [url-encode]]))

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

(defn sms-username []
  (System/getProperty "smsusername"))

(defn sms-password []
  (System/getProperty "smspassword"))

(defn escape-message [m]
  (url-encode m))


(defn build-url [message mobile]
  (str "https://www.textmagic.com/app/api?username=" (sms-username) "&password=" (sms-password) "&cmd=send&text=" (escape-message message) "&phone=" mobile "&unicode=0"))

(defn http-call-text-service [message mobile ]
  (:body (client/get (build-url message mobile))))

(defn send-text-to-all-members [message]
   (vec (map #(http-call-text-service message (:mobile %)) [(first (members))])))
   
(defn sms-username-password-set? []
  (not (or (nil? (sms-username)) (nil? (sms-password)))))

(defn get-username-password-error-message []
  (merge
   (if (nil? (sms-password)) {:password "Sms password not set"} {})
   (if (nil? (sms-username)) {:username "Sms username not set"} {})))

(defn send-member-text [message ]
  (if (sms-username-password-set?)
    (send-text-to-all-members message)
  (get-username-password-error-message)))