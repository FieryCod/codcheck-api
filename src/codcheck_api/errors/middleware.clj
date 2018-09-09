(ns codcheck-api.errors.middleware
  (:require
   [taoensso.timbre :as timbre]
   [codcheck-api.common.response :as common-response]))

(defn- handle-error
  [err]
  (let [ex-map (ex-data err)
        msg (.getMessage err)]

    (timbre/log :error msg)

    (if-not ex-map
      (common-response/server-err msg)
      (common-response/custom-err msg ex-map))))

(defn wrap-exception
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception err
           (handle-error err)))))
