(ns memjore.models.dbtest
  (:use [midje.sweet]
        [noir.util.test]
        [memjore.models.db]
        [memjore.models.validation]))

(def john  {:fname "John" :lname "Lewis" :mobile "07838334323" :phone "0268903234" :email "john@localhost.com" :address "No 1 somewhere" :tags "member exco"})

(fact "Should persist a member"
      (with-noir
        (let [m (add-member john)
              id (str (:_id m))]
          (:fname (get-member id)) => "John")))

(fact "Should return error message if member to be persisted is invalid"
      (with-noir 
        (:status (add-member john)) => "Error persisting member"
        (provided (is-valid john) => false)))


