(ns memjore.models.sms
    (:require [clj-http.client :as client])
    (:use [memjore.models.db]
          [hiccup.util :only [url-encode]]
          [noir.core :only [defpage url-for]]
          [noir.response :only [redirect]]
          [memjore.views.common :only [put-flash-message! layout get-flash-message]]
          [hiccup.form :only
           [label text-field form-to drop-down submit-button text-area]]))


(defn sms-username []
  (System/getProperty "smsusername"))

(defn sms-password []
  (System/getProperty "smspassword"))

(defn build-url [message mobile]
  (str
   "https://www.textmagic.com/app/api?username="
   (sms-username)
   "&password="
   (sms-password)
   "&cmd=send&text="
   (url-encode message)
   "&phone="
   mobile
   "&unicode=0"))

(defn http-call-text-service [message mobile ]
  (:body (client/get (build-url message mobile))))

(defn send-text-to-all-members [message]
   (vec (map #(http-call-text-service message (:mobile %)) (members))))

(defn sms-username-password-set? []
  (and (sms-username) (sms-password)))

(defn get-username-password-error-message []
  (merge
   (if (nil? (sms-password)) {:password "Sms password not set"} {})
   (if (nil? (sms-username)) {:username "Sms username not set"} {})))