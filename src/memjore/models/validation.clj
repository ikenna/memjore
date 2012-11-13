(ns memjore.model.validation
    (:use [noir.validation :as valid]))

(defn is-valid
  "Checks if input to create a member is valid"
  [m]
  (valid/rule (valid/has-value? (:fname m)) [:fname "first name is required"])
  (valid/rule (valid/has-value? (:lname m)) [:lname "last name is required"])
  (valid/rule (valid/has-value? (:address m)) [:address "address is required"])
  (valid/rule (valid/has-value? (:phone m)) [:phone "phone number is required"])
  (valid/rule (valid/has-value? (:mobile m)) [:mobile "mobile number is required"])
  (valid/rule (valid/has-value? (:tags m)) [:tags "tags are required"])
    
  (not (valid/errors?)))


(defn err-mess [field]
  (valid/on-error field str))
