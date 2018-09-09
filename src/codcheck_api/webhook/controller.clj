 (ns codcheck-api.webhook.controller
   (:require
    [compojure.core :as compojure]
    [codcheck-api.errors.def :as errors-def]
    [codcheck-api.common.middleware :as common-middleware]
    [codcheck-api.common.validator :as common-validator]
    [codcheck-api.common.helper :as common-helper]
    [codcheck-api.webhook.validator :as webhook-validator]
    [codcheck-api.webhook.service :as webhook-service]
    [codcheck-api.consts.path :as consts-path]
    [codcheck-api.consts.response :as consts-response]))

(compojure/defroutes webhook-controller
  (compojure/context consts-path/webhook []

    (compojure/POST "/" _
      (common-helper/before-request [common-validator/json-content-type
                                     webhook-validator/validate-gh-signature])

      (fn [{{:strs [x-github-event]} :headers :as request}]

        (condp common-helper/condp-some x-github-event
          ["installation"
           "installation_repositories"
           "integration_installation_repositories"] (webhook-service/handle-un-install request)

          ["pull_request"] (webhook-service/handle-pr request)

          (throw (errors-def/NotSupportedGhEvent
                  (str consts-response/not-supported-gh-event-msg x-github-event))))))))
