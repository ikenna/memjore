(ns memjore.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [include-css html5]]))

(defpage "/" []
  (common/login-layout
      (form-to [:post "/login-authentication"]
   (label "username" "Username")
   (text-field "username" "")
   [:br]
   (label "password" "Password")
   (text-field "password" )
   [:br]
   (submit-button "Submit"))))
