
(ns ^{:author "Ikenna Nwaiwu"}  memjore.views.home
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage]]))


(defpage "/manage/home" []
         (common/layout
          [:h2 "Home"]
          [:p "Number of members"]))
