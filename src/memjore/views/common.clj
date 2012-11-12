(ns memjore.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "memjore"]
               (include-css "/css/style.css") ]
              [:body
               [:div#wrapper
                [:div#header [:h1 "Memphis"]]
                content]]))
