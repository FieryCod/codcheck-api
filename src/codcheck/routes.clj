(ns codcheck.routes
  (:require [compojure.core :refer [routes defroutes context]]
            [compojure.route :refer [not-found]]
            [codcheck.controllers.home :refer [home-route]]))

(defonce ^{:doc "All api routes are prefixed with 'api-base-prefix URI"}
  api-base-prefix "/api/v1")

(defn not-found-route
  "Handler for all routes which does not exists in controllers"
  [request]
  {:status 404
   :headers {"content-type" "application/json; charset=UTF-8"}
   :body {:message (str "Route " (request :uri) " not found!")
          :status "RouteNotFound"}})

(defn api-routes
  "Prefix all routes with 'api-base-prefix"
  [routes-fns]
  (context api-base-prefix []
           (apply routes routes-fns)))

(def routing-controllers
  "All routes handlers"
  (routes (api-routes [home-route]) not-found-route))
