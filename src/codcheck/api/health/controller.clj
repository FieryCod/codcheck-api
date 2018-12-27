(ns codcheck.api.health.controller
  (:require
   [compojure.core :refer :all]
   [compojure.route]
   [codcheck.api.common.response :as response]
   [codcheck.api.path :as path]))

(defroutes health-controller
  (context path/health []

    (GET "/" []
      (response/success "API operational"))

    (GET "/favicon.ico" []
      (compojure.route/not-found "no favicon"))))
