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
        (:message (add-member john)) => "Error persisting member"
        (provided (is-valid john) => false)
        
        (:success (add-member john)) => false
        (provided (is-valid john) => false)))

(defn modify_fname_as_john_x [x]
  (let [m1 (dissoc x :_id)
        m2 (assoc m1 :fname "Johnx")]
    m2))

(fact "Should edit member"
      (with-noir
        (let [r0 (add-member john)
              id (str (:_id r0))              
              r1 (modify_fname_as_john_x r0)]
          
          (.getField (edit-member id r1) "updatedExisting") => true
          (provided (is-valid r1) => true)

          (:fname (get-member id)) => "Johnx")
          ))