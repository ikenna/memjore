(ns ^{:author "Ikenna Nwaiwu"} memjore.views.welcome
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage defpartial render url-for pre-route]]
        [noir.response :only [redirect]]
        [memjore.models.db :as db]
        [hiccup.form :only
         [label text-field form-to drop-down submit-button text-area hidden-field]]
        [hiccup.element :only [link-to]]
        [clojure.tools.trace :as tracer]))


(defn display_member [member]
      [[:td (:fname member)] [:td (:lname member)]])

(defn edit_button [member]
  [:td (link-to (url-for editpage {:id (:_id member)}) "Edit")])

(defn display-member-rows [members]
   (for [m members]
     [:tr
      [:td (:fname m)]
      [:td (:lname m)]
      [:td (:mobile m)]
      [:td (:address m)]
      (edit_button m)]))

(defn display-heading-row []
  [:tr
   [:th "First Name"]
   [:th "Last Name"]
   [:th "Mobile"]
   [:th "Address"]
   [:th ""]])

(defpage "/manage/members" []
  (common/layout
   [:h3 "All Members"]
   [:div.center
     [:table {:border 1}
	   (display-heading-row)
	   (display-member-rows (db/members)) ]]))
