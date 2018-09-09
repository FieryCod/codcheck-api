(ns codcheck-api.webhook.middleware
  (:require
    [clj-time.core :as clj-time]
    [buddy.sign.jwt :as jwt]
    [codcheck-api.webhook.auth :as webhook-auth]
    [codcheck-api.envs :refer [envs]]))


(defn- gh-token
  [claims]
  (jwt/sign claims webhook-auth/gh-private-key webhook-auth/jwt-algorithm))

(defn with-gh-token
  [handler]
  (fn [request]
    (let [claims {:exp (-> 10 clj-time/minutes clj-time/from-now)
                  :iat (clj-time/now)
                  :iss (:github-app-identifier envs)}
          token (gh-token claims)]

      (handler (assoc request :gh-app-token token)))))
