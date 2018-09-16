(ns codcheck-api.server
  (:require
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
   [codcheck-api.routes :refer [routing-controllers]]
   [codcheck-api.errors.middleware :refer [wrap-exception]]
   [codcheck-api.common.middleware :refer [wrap-raw-body wrap-not-found]]
   [codcheck-api.logging.middleware :refer [wrap-with-log-response wrap-with-log-request]]
   [codcheck.logging :as codcheck-logging]))

(def api-handler
  (-> routing-controllers
    wrap-not-found
    wrap-with-log-response
    wrap-with-log-request))

(def runnable-server
  "Runnable server instance"
  (do
   (codcheck-logging/setup-logging "codcheck-api")
   (-> (wrap-defaults api-handler api-defaults)
      (wrap-exception)
      (wrap-json-response)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-raw-body))))
