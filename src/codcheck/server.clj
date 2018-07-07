(ns codcheck.server
  (:require [codcheck.middlewares.logging :refer [log-request]]
            [codcheck.routes :refer [routing]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [codcheck.error-handler :refer [wrap-exception]]))

(def runnable-server
  "Runnable server instance"
  (-> (wrap-defaults (log-request routing) api-defaults)
     wrap-json-response
     wrap-exception))
