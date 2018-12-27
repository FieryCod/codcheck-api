(ns codcheck.api.development.middleware
  (:require
   [codcheck.envs :refer [envs]]
   [codcheck.api.common.error :as error]))

(defn only-development
  [handler]
  (fn [request]
    (if (= (:clj-env envs) "development")
      (handler request)
      (throw (error/RouteNotFound (:uri request))))))
