(ns codcheck-api.rmq.core
  (:require [langohr.core :as langohr]
            [codcheck-api.envs :refer [envs]]))

(def conn
  (let [{:keys [rmq-user rmq-pass rmq-host]} envs]
    (langohr/connect {:host rmq-host
                      :username rmq-user
                      :password rmq-pass})))
