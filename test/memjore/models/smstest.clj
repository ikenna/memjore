(ns memjore.models.dbtest
  (:require [monger core collection])
  (:use [midje.sweet]
        [noir.util.test]
        [memjore.models.db]
        [memjore.models.sms]))

(fact "Should error if text username or password not set"
      (do
        (System/clearProperty "smsusername")
        (System/clearProperty "smspassword")        
        (send-member-text "Sampletext") =>  {:username "Sms username not set", :password "Sms password not set"}))


;;(fact "Should send real text"
;;      (do
;;        (System/setProperty "smsusername" "x")
;;        (System/setProperty "smspassword" "x")
;;        (http-call-text-service "hello world" "x") => "hello world"
;;      ))