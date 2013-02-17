(ns memjore.models.email
  (:use [memjore.models.db]
        [postal.core :only [send-message]]))



(defn get-from [x]
  (System/getProperty "fromemail"))

(defn smtp-server []
  {:host "smtp.gmail.com"
  :user "xxxx"
  :pass "xxxx"
  :port 465
  :ssl :yes})

(defn email []
  {:from ""
   :to ""
   :subject ""
   :body ""})

(defn send-email-message [smtp-host email]
  (send-message (with-meta email smtp-host)))

(defn send-email [member message from]
  (send-message (with-meta (smtp-server) (email))))

(defn send-email-to-all [message]
  (map #(send-email % message (get-from)) (members) ))
