(ns codcheck.api.webhook.controller
  (:require
   [compojure.core :refer :all]
   [codcheck.api.common.error :as error]
   [codcheck.api.common.helper :as helper]
   [codcheck.api.common.macro :as macro]
   [codcheck.api.webhook.validator :as webhook-validator]
   [codcheck.api.webhook.service :as webhook-service]
   [taoensso.timbre :as timbre]
   [codcheck.api.path :as path]))

(defroutes webhook-controller
  (context path/webhook []

    (POST "/" _
      (macro/bind-request-middlewares

       [webhook-validator/validate-gh-signature]

       (fn [{{:strs [x-github-event]} :headers :as request}]

         (timbre/info "Event" x-github-event "received")

         (condp helper/condp-some x-github-event

           ["installation"
            "installation_repositories"
            "integration_installation_repositories"] (webhook-service/handle-un-install request)

           ["pull_request"] (webhook-service/handle-pr request)

           (throw (error/NotSupportedGhEvent
                   (str "Event is not supported: " x-github-event)))))))))
