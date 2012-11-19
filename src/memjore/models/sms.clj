(ns memjore.models.sms
    (:require [clj-http.client :as client])
    (:use [memjore.models.db]
          [hiccup.util :only [url-encode]]))


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