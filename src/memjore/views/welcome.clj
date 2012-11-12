(ns memjore.views.welcome
  (:require [memjore.views.common :as common]
             [monger core collection])
  (:use [noir.core :only [defpage defpartial]]
         [noir.response :only [redirect]]
        [hiccup.form :only [label text-field form-to drop-down submit-button text-area]]
         [hiccup.element :only [link-to]]))


(defpage "/" []
         (common/layout
	   [:p "Welcome"]
           [:a {:href "/members"} "Show Members"]))


(monger.core/connect!)
(monger.core/set-db! (monger.core/get-db "test"))


(defn get-member [n]
  (first (monger.collection/find-maps "members" {:id n})))

(defn add-member [n])

(defn members []
  (monger.collection/find-maps "members" ))

(defn display_member [member]
      [[:td (:fname member)] [:td (:lname member)]])

(defn edit_button [member]
       [:td [:a {:href (str "/member/edit/" (:id member))} "Edit"]] )

(defpage "/members" []
  (common/layout
   [:p (link-to "/member/add" "Add Member")]
   
   [:h3 "All Members"]
   [:div {:class "center" }
     [:table {:border 1}
	     [:tr [:th "First Name"] [:th "Last Name"][:th "Mobile"] [:th "Address"][:th ""]]
	    (for [m (members)]
              [:tr [:td (:fname m)] [:td (:lname m)] [:td (:mobile m)][:td (:addr m)] (edit_button m)]) ]]))



(defpartial user-fields [{:keys [fname lname mobile phone address tags]}]
  [:div#editform
   [:p   (label "firstname" "First name:") (text-field "fname" fname)]
   [:p   (label "lastname" "Last name:") (text-field "lname" lname)]
   [:p   (label "mobile" "Mobile:") (text-field "mobile" mobile)]
   [:p   (label "phone no" "Phone number:") (text-field "phone" phone)]
   [:p   (label "address" "Address:") (text-area "address" address) ]
   [:p   (label "tags" "Tags:")     (text-area "tags" tags)] ]
   [:p   (submit-button "Submit") ])


(defpartial edit-form-heading []
  [:h2 "Edit Member"] )

(defpage "/member/edit/:id" {:keys [id]}
  (common/layout
   (edit-form-heading)
   (form-to [:post "/user/add"]
   (user-fields (get-member (Integer/valueOf id))))))

(defpage [:get "/member/add"] []
  (common/layout
   [:h2 "Add Member"]
   (form-to [:post "/member/add"]
   (user-fields {}))))

;;Checks if input to create a member is valid
;;use noir validation framework to implement this
(defn is-valid [m]
  true)

(defpage [:post "/member/add"] {:keys [fname lname address mobile tags phone] :as input} 
  ;;validate
  (if (is-valid input)
  ;;insert
   (monger.collection/insert "members" input ))
  
  ;;redirect to members page
  (redirect "/members"))
  
;;  (common/layout (str "hi" member)))
