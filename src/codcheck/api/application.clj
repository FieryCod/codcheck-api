(ns codcheck.api.application
  (:require
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
   [codcheck.logging :refer [wrap-with-log-response wrap-with-log-request]]
   [codcheck.api.route :refer [routing-controllers]]
   [codcheck.api.common.middleware :refer [wrap-exception
                                           wrap-raw-body
                                           wrap-not-found
                                           json-content-type]]))

(def ^:private api-handler
  (-> routing-controllers
      wrap-not-found
      json-content-type
      wrap-with-log-response
      wrap-with-log-request))

(defn setup-app-handler
  []
  (-> (wrap-defaults api-handler api-defaults)
      (wrap-exception)
      (wrap-json-response)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-raw-body)))
