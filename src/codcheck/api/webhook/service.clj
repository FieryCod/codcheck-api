(ns codcheck.api.webhook.service
  (:require
   [langohr channel basic]
   [codcheck.rmq]
   [codcheck.api.common.response :as response]
   [codcheck.api.common.error :as error]))

(defn- publish-gh-pr-event
  [request]
  (let [exchange (:pr-code-check codcheck.rmq/exchanges)
        routing-key (:pr-code-check codcheck.rmq/routing-keys)
        event-body (str (:body (dissoc request :raw-body)))]

    (langohr.basic/publish @codcheck.rmq/chan exchange routing-key event-body)
    (response/success "Pull reqeust has been submitted")))

(defn handle-pr
  [{{:keys [action]} :body :as request}]

  (condp = action

    "opened"
    (publish-gh-pr-event request)

    "closed"
    (response/success "Pull request has been closed")

    (throw
     (error/NotSupportedGhAction
      (str "Action is not supported: " action)))))


(defn handle-un-install
  [request]
  (if (= (get-in request [:body :action] "") "removed")
    (response/success "Codcheck successfully uninstalled :((")
    (response/success "Codcheck successfully installed ;)")))
