(ns codcheck-api.webhook.auth
  (:require
   [codcheck-api.envs :refer [envs]]
   [buddy.core.keys :as buddy-keys])
  (:import
   [org.apache.commons.codec.digest HmacUtils HmacAlgorithms]))

(def gh-sha1-generator
  (HmacUtils. HmacAlgorithms/HMAC_SHA_1 (:github-webhook-secret envs)))

(def jwt-algorithm
  {:alg :rs256})

(def gh-private-key
  (buddy-keys/str->private-key (:github-private-key envs)))
