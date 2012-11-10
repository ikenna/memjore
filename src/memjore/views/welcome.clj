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



(defn members []
  (monger.collection/find-maps "members" ))


(defn display_member [member]
      [[:td (:fname member)] [:td (:lname member)]])

(defn edit_button [member]
       [:td [:a {:href (str "/member/edit/" (:id member))} "Edit"]] )

(defpage "/members" []
         (common/layout
	[:div {:class "center" }
	   [:table {:border 1}
	     [:tr [:th "First Name"] [:th "Last Name"][:th "Mobile"] [:th "Address"][:th ""]]
	    (for [m (members)]
	     [:tr [:td (:fname m)] [:td (:lname m)] [:td (:mobile m)][:td (:addr m)] (edit_button m)]) ]]))