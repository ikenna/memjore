(ns memjore.views.logintest
  (:require [noir.session :as session])
  (:use [midje.sweet]
        [noir.util.test]
        [memjore.views.login]))


(fact "User is not logged in when session username is nil"
  (with-noir
    (do
      (session/clear!)
      (logged-in?) => false)))


(fact "User is logged in when session username is not nil"
  (with-noir
    (do
      (session/put! :username "somebody")
      (logged-in?) => true)))

