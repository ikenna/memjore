(ns
    ^{:author "Ikenna Nwaiwu"
      :doc "This file contains the web display elements of the app "}
    memjore.views.welcome
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage defpartial render url-to]]
        [noir.response :only [redirect]]
        [memjore.models.db :as db]
        [memjore.models.validation]
        [hiccup.form :only
         [label text-field form-to drop-down submit-button text-area]]
        [hiccup.element :only [link-to]]
        [clojure.tools.trace :as tracer]))


(defpage "/" []
         (common/layout
	   [:p "Welcome"]
           [:a {:href "/members"} "Show Members"]))

(defn display_member [member]
      [[:td (:fname member)] [:td (:lname member)]])

(defn edit_button [member]
  [:td (link-to (url-for editpage {:id (:id member)}) "Edit")]) 

(defn display-member-rows [members]
   (for [m members]
     [:tr
      [:td (:fname m)]
      [:td (:lname m)]
      [:td (:mobile m)]
      [:td (:addr m)]
      (edit_button m)]))

(defn display-heading-row []
  [:tr
   [:th "First Name"]
   [:th "Last Name"]
   [:th "Mobile"]
   [:th "Address"]
   [:th ""]])

(defpage "/members" []
  (common/layout
   [:p (link-to "/member/add" "Add Member")]
   [:h3 "All Members"]
   [:div.center
     [:table {:border 1}
	   (display-heading-row)
	   (display-member-rows (members)) ]]))

(defn edit-text-field [f]
  (let [[symbol id name areabox] f]
    [:p (label id name)
     (if (nil? areabox)(text-field symbol) (text-area symbol) )
     (err-mess symbol)]))


(defpartial user-fields [{:keys [fname lname mobile phone address tags]}]
  [:div#editform
   (map edit-text-field [[:fname "firstname" "First Name:"]
                     [:lname "lastname" "Last Name:"]
                     [:mobile "mobile" "Mobile:"]
                     [:phone "phone number" "Phone Number:"]
                     [:address "address" "Address:" :area-box]
                     [:tags "tags" "Tags:" :area-box]])
   [:p   (submit-button "Submit") ]])


(defpage editpage "/member/edit/:id" {:keys [id]}
  (common/layout
   [:h2 "Edit Member"] 
   (form-to [:post "/user/add"]
   (user-fields (get-member (Integer/valueOf id))))))


(defpage [:get "/member/add"] {:keys [error] :as params}
  (common/layout
   [:h2 "Add Member"]
   (form-to [:post "/member/add"]
   (user-fields {}))))


(defpage [:post "/member/add"] {:as input} 
  (if (is-valid input)
    (do
      (db/add-member input)
      (redirect "/members"))
    (render "/member/add" input)))
  


 