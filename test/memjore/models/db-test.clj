(ns memjore.models.db-test
  (:use [midje.sweet]
        [memjore.models.db]))

(def john  {:fname "John" :lname "Lewis" :mobile "07838334323" :phone "0268903234" :email "john@localhost.com" :address "No 1 somewhere" :tags "member exco"})

(fact "Should persist a member"
 (let [m (add-member john)
       id (str (:_id m))]
   (:fname (get-member id)) => "John"))
