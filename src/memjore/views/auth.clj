(ns
    ^{:author "Ikenna Nwaiwu"
      :doc "This file contains the web display elements of the app "}
    memjore.views.welcome
  (:require [memjore.views.common :as common])
  (:use [noir.core :only [defpage defpartial render url-for pre-route]]
        [noir.response :only [redirect]]
        [memjore.models.db :as db]
        [memjore.models.validation]
        [hiccup.form :only
         [label text-field form-to drop-down submit-button text-area hidden-field]]
        [hiccup.element :only [link-to]]
        [clojure.tools.trace :as tracer]))

(defn logged-in? [] false)

(pre-route "/manage/*" {}
           (when-not (logged-in?)
             (redirect "/")))
