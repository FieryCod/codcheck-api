(ns codcheck-api.webhook.service
  (:require
   [langohr.channel :as langohr-chan]
   [langohr.basic :as langohr-basic]
   [codcheck-api.consts.response :as consts-response]
   [codcheck-api.common.response :as common-response]
   [codcheck-api.errors.def :as errors-def]
   [codcheck-api.rmq.core :as rmq]
   [codcheck-api.rmq.config :as rmq-config]))

(defn- code-check-event
  [request]
  (let [chan (langohr-chan/open rmq/conn)
        event-key :gh-pr-code-check]

    (langohr-basic/publish chan
                           (event-key rmq-config/exchanges)
                           (event-key rmq-config/routing-keys)
                           (str request))))

(defn handle-pr
  [{{:keys [action]} :body :as request}]
  (condp = action
    "opened" (code-check-event request)
    (throw (errors-def/NotSupportedGhAction (str consts-response/not-supported-action-msg action))))

  (common-response/success consts-response/success-pr-event-msg))

(defn handle-un-install
  [request]
  (if (= (get-in request [:body :action] "") "removed")
    (common-response/success consts-response/uninstall-app-msg)
    (common-response/success consts-response/install-app-msg)))
