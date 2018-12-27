(ns codcheck.api.webhook.middleware
  (:require
    [codcheck.auth]
    [codcheck.envs :refer [envs]]))

(defn with-gh-token
  [handler]
  (fn [request]
    (handler (assoc request :gh-app-token (codcheck.auth/gh-sign-token)))))
