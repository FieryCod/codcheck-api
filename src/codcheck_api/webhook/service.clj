(ns codcheck-api.webhook.service
  (:require
   [langohr.channel :as langohr-chan]
   [langohr.basic :as langohr-basic]
   [codcheck-api.consts.response :as consts-response]
   [codcheck-api.common.response :as common-response]
   [codcheck-api.errors.def :as errors-def]
   [codcheck.rmq :as codcheck-rmq]))

(defn- code-check-event
  [request]
  (let [event-key :gh-pr-code-check]

    (langohr-basic/publish @codcheck-rmq/chan
                           (event-key codcheck-rmq/exchanges)
                           (event-key codcheck-rmq/routing-keys)
                           (str request))))

(defn handle-pr
  [{{:keys [action]} :body :as request}]

  (condp = action
    "opened" (code-check-event request)
    "closed" (common-response/success consts-response/pr-closed-msg)

    (throw (errors-def/NotSupportedGhAction (str consts-response/not-supported-action-msg action)))))


(defn handle-un-install
  [request]
  (if (= (get-in request [:body :action] "") "removed")
    (common-response/success consts-response/uninstall-app-msg)
    (common-response/success consts-response/install-app-msg)))
