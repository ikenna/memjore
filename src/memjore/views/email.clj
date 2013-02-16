
(ns ^{:author "Ikenna Nwaiwu"} memjore.views.email
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.form :only [form-to text-area submit-button]]))

(defpage email "/manage/sendemail" []
  (common/layout
   [:h2 "Send Email" ]

 (form-to [:post "sendemail"]
            (text-area "editor")
            [:p]
            (submit-button "Submit"))
 [:script "CKEDITOR.replace('editor');"]
 ))
