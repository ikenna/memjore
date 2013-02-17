(ns memjore.models.emailtest
  (:import [com.dumbster.smtp.SimpleSmtpServer])
  (:use [midje.sweet]
        [postal.core :only [send-message]]
        [memjore.models.email]
        ))

(def port 1241)
(def server  (com.dumbster.smtp.SimpleSmtpServer/start port))


(fact "Can send email"
      (let [host {:host "localhost" :port port}
            message {:from "test@test.com" :to "test@test.com"
                     :subject "email subject" :body "some email body"}
            result (send-email-message host message)
            no-emails (. server getReceivedEmailSize)
            mail  (. (. server getReceivedEmail) next)
            body (. mail getBody)
            subject (. mail getHeaderValue "Subject")]

        body => "some email body"
        subject => "email subject"))


(. server stop)
