(ns memjore.models.validation
    (:use [noir.validation :as valid]))

(defn is-valid
  "Checks if input to create a member is valid"
  [m]
  (valid/rule (valid/has-value? (:fname m)) [:fname "first name is required"])
  (valid/rule (valid/has-value? (:lname m)) [:lname "last name is required"])
  (valid/rule (valid/has-value? (:tags m)) [:tags "tags are required"])
    (not (valid/errors?)))


(defn err-mess [field]
  [:span.error
   (valid/on-error field (fn[m] (str "*" (first m))))])
