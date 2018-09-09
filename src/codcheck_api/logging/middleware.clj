(ns codcheck-api.logging.middleware
  (:require [taoensso.timbre :as timbre]))

(defn- log-request
  "Logs the request"
  [{:keys [request-method uri]}]
  (timbre/info
   (str "Started [req " request-method "] [path " uri "]")))

(defn- log-response
  "Logs the request"
  [{:keys [request-method uri] :as request}
   {:keys [status body] :as response}]
  (timbre/info
   (str "Ended [req " request-method "] [path " uri "] [s " status "] [bd " body "]")))

(defn wrap-with-log-request
  "Wraps the handler and logs the request"
  [handler]
  (fn [request]
    (log-request request)
    (handler request)))

(defn wrap-with-log-response
  "Wraps the handler and logs the response"
  [handler]
  (fn [request]
    (try
      (let [response (handler request)]
        (log-response request response)
        response)
      (catch Exception err
        (throw err)))))
