(ns codcheck-api.development.middleware
  (:require
   [codcheck.envs :refer [envs]]
   [codcheck-api.errors.def :as errors-def]))

(defn only-development
  [handler]
  (fn [request]
    (if (= (:clj-env envs) "development")
      (handler request)
      (throw (errors-def/RouteNotFound (:uri request))))))
