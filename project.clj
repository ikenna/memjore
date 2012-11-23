(defproject memjore "0.1.0-SNAPSHOT"
            :description "Memjore - member management software"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.3.0-beta3"]
                           [org.clojure/tools.trace "0.7.3"]
                           [com.draines/postal "1.9.0"]
                           [clj-http "0.5.8"]
                           [org.clojure/data.json "0.2.0"]
                           [com.novemberain/monger "1.3.1"]]

            
            :plugins [[lein-pprint "1.1.1"]
                      [lein-swank "1.4.0"]
                      [lein-assoc "0.1.0"]
                      [lein-midje "2.0.1"]]

            :profiles {:dev
                       {:dependencies [[midje "1.4.0"]]}}
             
            :main memjore.server)

