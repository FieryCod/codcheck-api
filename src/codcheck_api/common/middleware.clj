(ns codcheck-api.common.middleware
  (:require
   [compojure.core :as compojure]
   [codcheck-api.errors.def :as errors-def])
  (:import [java.io ByteArrayInputStream]))

(defn wrap-not-found
  "Handler for all routes which does not exists in controllers"
  [handler]
  (compojure/routes
   handler
   (fn [request]
     (throw (errors-def/RouteNotFound (str "Route " (request :uri) " not found!"))))))

(defn wrap-raw-body
  [handler]
  (fn [request]
    (let [raw-body (slurp (:body request))
          encoding "UTF-8"
          body (ByteArrayInputStream. (.getBytes raw-body encoding))]
      (handler (assoc request :raw-body raw-body :body body)))))
