
(ns ^{:author "Ikenna Nwaiwu"} memjore.views.email
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.form :only [form-to text-area submit-button]]))


(defpage emailcontroller [:post "/manage/emailcontroller"] {:keys [email-editor]}
  (common/layout
   [:p email-editor]))


(defpage email "/manage/sendemail" []
  (common/layout
   [:h2 "Send Email" ]

   (form-to [:post "/manage/emailcontroller"]
            (text-area "email-editor")
            [:p]
            (submit-button "Submit"))
   [:script "CKEDITOR.replace('email-editor');"]
   ))
