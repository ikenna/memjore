(ns memjore.models.email
  (:use [memjore.models.db]
        [postal.core :only [send-message]]))



(defn get-from [x]
  (System/getProperty "fromemail"))

(defn send-email [member message from]
  (send-message ^{:host "smtp.gmail.com"
                  :user "mail"
                  :pass "nacfukexco"
                  :port 465
                  :ssl :yes!!!11}
                {:from from
                 :to [(:email member)]
                 :subject (:subject message)
                 :body (:body message)}))

(defn send-email-to-all [message]
  (map #(send-email % message (get-from)) (members) ))





