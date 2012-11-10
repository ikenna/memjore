(ns memjore.views.welcome
  (:require [memjore.views.common :as common]
             [monger core collection])
  (:use [noir.core :only [defpage]]))


(defpage "/" []
         (common/layout
	   [:p "Welcome"]
           [:a {:href "/members"} "Show Members"]))


(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [n]
  (first (monger.collection/find-maps "members" {:id n})))