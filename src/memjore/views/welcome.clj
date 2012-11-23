(ns
    ^{:author "Ikenna Nwaiwu"
      :doc "This file contains the web display elements of the app "}
    memjore.views.welcome
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage defpartial render url-for]]
        [noir.response :only [redirect]]
        [memjore.models.db :as db]
        [memjore.models.validation]
        [hiccup.form :only
         [label text-field form-to drop-down submit-button text-area hidden-field]]
        [hiccup.element :only [link-to]]
        [clojure.tools.trace :as tracer]))


(defpage "/" []
         (common/layout
	   [:h2 "Home"]))

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
   [:h3 "All Members"]
   [:div.center
     [:table {:border 1}
	   (display-heading-row)
	   (display-member-rows (members)) ]]))

(defn edit-text-field [f]
  (let [[symbol value id name areabox] f]
    [:p (label id name)
     (if (nil? areabox)(text-field symbol value) (text-area symbol value) )
     (err-mess symbol)]))


(defpartial user-fields [{:keys [id fname lname mobile phone address tags] :as member}]
  [:div#editform
   (map edit-text-field [
                         [:fname fname "firstname" "First Name:"]
                         [:lname lname "lastname" "Last Name:"]
                         [:mobile mobile "mobile" "Mobile:"]
                         [:phone phone "phone number" "Phone Number:"]
                         [:address address "address" "Address:" :area-box]
                         [:tags tags "tags" "Tags:" :area-box]])
   [:p  (hidden-field "id" (:_id member)) (submit-button "Submit") ]])


(defpage sendtext "/sendtext" []
  (common/layout
   [:h2 "Send a text" ]))

(defpage homepage "/sendemail" []
  (common/layout
   [:h2 "Send Email" ]))

(defpartial display-error-messages-if-any [req]
  (if (not (= (:success req) true))
    [:h3.error (:message req)]))
    


(defpage editpage "/members/edit/:id" {:keys [id] :as req}
  (common/layout
   [:h2 "Edit Member"]
   (display-error-messages-if-any req)
   (form-to [:post "/members/editpagehandler/"]
            (user-fields (get-member id)))))



(defpage editpagehandler [:post "/members/editpagehandler/"] {:as req}
   (do
     (let [id (:id req)
           result (db/edit-member id req)]
       (if (:success result)
         (redirect "/members")
         (editpage (merge req result))))))

(defpage [:get "/members/add"] {:keys [error] :as params}
  (common/layout
   [:h2 "Add Member"]
   (form-to [:post "/members/add"]
   (user-fields {}))))


(defpage [:post "/members/add"] {:as input} 
  (if (:sucess (db/add-member input))
    (redirect "/members")
    (render "/members/add" input)))
  


 