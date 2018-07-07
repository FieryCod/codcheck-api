(ns codcheck.error-handler
  (:require [taoensso.timbre :as timbre]))

(defn handle-error
  "Handles errors of app"
  [err]
  (let [msg (.getMessage err)]
    (timbre/log :error msg)
    {:status 500
     :body {:msg (str "Internal server error: " msg)}}))

(defn wrap-exception
  "Wraps the ring-handler with the error-handler"
  [handler]
  (fn [request]
    (try (handler request)
         (catch Exception err
           (handle-error err)))))
