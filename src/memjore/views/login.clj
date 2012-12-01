(ns memjore.views.login
  (require [noir.session :as session])
  (:use [noir.core :only [defpartial defpage url-for]]
        [noir.response :only [redirect]]
        [memjore.views.common :only [footer get-flash-message put-flash-message!]]
        [memjore.views.home :only [home]]
        [hiccup.form :only
         [label text-field form-to drop-down submit-button text-area hidden-field password-field]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [include-css html5]]))


(defpartial login-layout [& content]
  (html5
   [:head
    [:title "Welcome to Memjore"]
    (include-css "/css/style.css") ]
    [:body
     [:div#wrapper
      [:div#header
       [:h1 "Welcome to Memjore"]
       [:div#content content]
       [:div#footer (footer)]]]]))

(defpage login "/"  []
  (login-layout
   [:p.error (get-flash-message)]
      (form-to [:post "/login-authentication"]
   (label "username" "Username")
   (text-field "username" "")
   [:br]
   (label "password" "Password")
   (password-field "password" )
   [:br]
   (submit-button "Submit"))))


(defn logged-in? []
  (boolean (session/get :username)))

(defn authenticates? [username password]
  (and (= username "admin") (= "password")))

(defpage login-authentication [:post "/login-authentication"] {:keys [username password]}
  (if (authenticates? username password)

    (do
      (session/put! :username username)
      (put-flash-message! (str "Welcome " username))
      (redirect (url-for home)))

    (do
      (put-flash-message! "Username or password invalid")
      (redirect (url-for login)))))

(defpage log-out "/log-out" []
  (do
    (session/clear!)
    (login-layout
     [:p.center "Logged out"]
     [:p.center (link-to "/" "Log in")])))