(ns memjore.models.db-test
  (:use [midje.sweet]
        [memjore.models.db]))

(fact
 (let [m0 {:fname "John" :lname "Lewis"}
       m1 (add-member m0)
       id (str (:_id m1))]
   (:fname (get-member id)) => "John"))
