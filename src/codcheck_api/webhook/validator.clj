(ns codcheck-api.webhook.validator
  (:require
   [codcheck-api.webhook.auth :as webhook-auth]
   [codcheck-api.errors.def :as errors-def]
   [clojure.string :as string]))

(defn validate-gh-signature
  [handler]
  (fn [request]
    (let [xhub-header (get-in request [:headers "x-hub-signature"])]
      (when (nil? xhub-header)
        (throw (errors-def/InvalidGhSignature "No x-hub-signature found")))

      (let [[method gh-signature] (string/split xhub-header #"=")]
        (when (not= method "sha1")
          (throw (errors-def/InvalidGhSignature "Wrong method used")))

        (when (not= gh-signature (.hmacHex webhook-auth/gh-sha1-generator (:raw-body request)))
          (throw (errors-def/InvalidGhSignature "Request signature does not match with API one")))

        (handler request)))))
