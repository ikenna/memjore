
(ns ^{:author "Ikenna Nwaiwu"} memjore.views.text
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage]]))


(defpage sendtext "/manage/sendtext" []
  (common/layout
   [:h2 "Send a text" ]))
