
(ns ^{:author "Ikenna Nwaiwu"}  memjore.views.home
    (:require [memjore.views.common :as common]
              [noir.session :as session])
  (:use [noir.core :only [defpage]]))


(defpage home "/manage/home" []
         (common/layout
          [:h2 "Home"]
          [:p (common/get-flash-message)]
          [:p "Number of members"]))
