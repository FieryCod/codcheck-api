(ns codcheck.api.common.middleware
  (:require
   [compojure.core]
   [taoensso.timbre :as timbre]
   [codcheck.api.common.error :as error]
   [codcheck.api.common.response :as response])
  (:import
   [java.io ByteArrayInputStream]))

(defn wrap-not-found
  "Handler for all routes which does not exists in controllers"
  [handler]
  (compojure.core/routes handler
                         (fn [request]
                           (throw (error/RouteNotFound (request :uri))))))

(defn wrap-raw-body
  [handler]
  (fn [request]
    (let [raw-body (slurp (:body request))
          encoding "UTF-8"
          body (ByteArrayInputStream. (.getBytes raw-body encoding))]
      (handler (assoc request :raw-body raw-body :body body)))))

(defn- handle-error
  [err]
  (let [ex-map (ex-data err)
        msg (.getMessage err)]

    (timbre/log :error msg)

    (if-not ex-map
      (response/server-err msg)
      (response/custom-err msg ex-map))))

(defn wrap-exception
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception err
        (handle-error err)))))

(defn json-content-type
  [handler]
  (fn [request]
    (when (not= (get-in request [:headers "content-type"]) "application/json")
      (throw (error/InvalidContentType "Content type should be application/json")))
    (handler request)))
