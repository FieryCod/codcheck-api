(ns codcheck.api.route
  (:require
   [compojure.core]
   [codcheck.api.health.controller :refer [health-controller]]
   [codcheck.api.webhook.controller :refer [webhook-controller]]
   [codcheck.api.development.controller :refer [development-controller]]))

(def routing-controllers
  (apply compojure.core/routes
         [health-controller
          development-controller
          webhook-controller]))
