(ns codcheck.server
  (:require [codcheck.middlewares.logging :refer [log-request]]
            [codcheck.routes :refer [routing-controllers]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [codcheck.error-handler :refer [wrap-exception]]))

(def runnable-server
  "Runnable server instance"
  (-> (wrap-defaults (log-request routing-controllers) api-defaults)
     wrap-json-response
     wrap-exception))
