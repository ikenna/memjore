(ns memjore.server
  (:require [noir.server :as server]
            [monger core collection])
  (:use [noir.core :only [pre-route]]
        [noir.response :only [redirect]]
        [memjore.views.login :only [logged-in?]]))

(server/load-views-ns 'memjore.views)

(monger.core/connect-via-uri! (get-property :mongo-url))

(pre-route "/manage/*" {}
           (when-not (logged-in?)
             (redirect "/")))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'memjore})))

