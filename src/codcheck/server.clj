(ns codcheck.server
  (:require [codcheck.middlewares.logging :refer [log-request]]
            [codcheck.routes :refer [routing-controllers]]
            [codcheck.config.timbre :refer [setup-timbre]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [codcheck.error-handler :refer [wrap-exception]]))

(defn setup-server
  "Setups the server"
  []
  (setup-timbre)
  (wrap-defaults (log-request routing-controllers) api-defaults))

(def runnable-server
  "Runnable server instance"
  (-> (setup-server)
     wrap-json-response
     wrap-exception))
