(ns memjore.views.welcome
  (:require [memjore.views.common :as common]
             [monger core collection])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.form :only [label text-field form-to]]))


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


(defpartial user-fields [{:keys [fname lname]}]
  (label "firstname" "First name:")
  (text-field "firstname" fname)
  (label "lastname" "Last name:")
  (text-field "lastname" lname))

(defpage "/member/edit/:id" {:keys [id]}
  (common/layout
   (form-to [:post "/user/add"]
   (user-fields (get-member (Integer/valueOf id))))))
