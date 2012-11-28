
(ns ^{:author "Ikenna Nwaiwu"} memjore.views.email
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage]]
        [hiccup.form :only [form-to]]))

(defpage email "/manage/sendemail" []
  (common/layout
   [:h2 "Send Email" ]))
