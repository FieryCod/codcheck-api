(ns codcheck.middlewares.logging
  (:require [taoensso.timbre :as timbre]
            [ring.logger :as ring-logger]))

(defn log-fn [{:keys [level throwable message]}]
  (timbre/log level throwable message))

(defn log-request [handler]
  (ring-logger/wrap-log-response handler {:log-fn log-fn :log-exceptions? false}))
