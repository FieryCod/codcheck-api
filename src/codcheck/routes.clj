(ns codcheck.routes
  (:require [compojure.core :refer [routes defroutes]]
            [compojure.route :refer [not-found]]
            [codcheck.controllers.home :refer [home-route]]))

(defn not-found-route
  "Handler for all routes which does not exists in controllers"
  [request]
  {:status 404
   :headers {"content-type" "application/json; charset=UTF-8"}
   :body {:message (str "Route " (request :uri) " not found!")
          :status "RouteNotFound"}})

(defn routing-controllers
  "Defines the routing for controllers"
  [controllers]
  (apply routes (conj controllers not-found-route)))

(def routing
  "All routes handlers"
  (routing-controllers [home-route]))
