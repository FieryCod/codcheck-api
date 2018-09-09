(ns codcheck-api.common.validator
  (:require
   [codcheck-api.errors.def :as errors-def]))

(defn json-content-type
  [handler]
  (fn [request]
    (when (not= (get-in request [:headers "content-type"]) "application/json")
      (throw (errors-def/InvalidContentType "Content type should be application/json")))
    (handler request)))
