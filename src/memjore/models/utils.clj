
(ns memjore.models.utils)

(defn get-property-file []
  (if (System/getenv "IS_PROD")
    "/prod-properties.config"
    "dev-properties.config"))

(defn get-property [name]
    ((keyword name) (read-string (slurp (get-property-file)))))

