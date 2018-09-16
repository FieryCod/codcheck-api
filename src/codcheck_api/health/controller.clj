(ns codcheck-api.health.controller
  (:require
   [compojure.route]
   [compojure.core :as compojure]
   [codcheck-api.consts.path :as consts-path]
   [codcheck-api.common.response :as common-response]))

(compojure/defroutes health-controller
  (compojure/context consts-path/health []

    (compojure/GET "/" []
      (common-response/success "API operational"))

    (compojure/GET "/favicon.ico" []
      (compojure.route/not-found "no favicon"))))
