(ns codcheck-api.routes
  (:require
   [compojure.core :as compojure]
   [codcheck-api.health.controller :refer [health-controller]]
   [codcheck-api.webhook.controller :refer [webhook-controller]]))

(def routing-controllers
  "Routing controllers handler"
  (apply compojure/routes [health-controller
                           webhook-controller]))
