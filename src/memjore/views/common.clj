(ns memjore.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [include-css html5]]))

(defn main-links [links]
  "Returns links to main pages of the app"
  (map
   #(let [[url text] %]
         [:span.main-menu-links (link-to url text)])
   links))

(defpartial footer []
  [:p (str "Memjore. Copyright Ikenna Nwaiwu") ])

(defpartial layout [& content]
            (html5
              [:head
               [:title "memjore"]
               (include-css "/css/style.css") ]
              [:body
               [:div#wrapper
                [:div#header
                 [:h1 "Memjore"]
                 [:p.main-menu
                  (main-links [  ["/" "Home"]
                                 [ "/members" "All Members"]
                                 [ "/members/add" "Add Member"]
                                 [ "/sendtext" "Send Text"]
                                 [ "/sendemail" "Send Email"]])]]
                [:div#content content]
                [:div#footer (footer)]]]))
