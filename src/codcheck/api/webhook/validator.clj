(ns codcheck.api.webhook.validator
  (:require
   [codcheck.auth :as codcheck-auth]
   [codcheck.api.common.error :as error]
   [clojure.string :as string]))

(defn validate-gh-signature
  [handler]
  (fn [request]
    (let [xhub-header (get-in request [:headers "x-hub-signature"])]
      (when (nil? xhub-header)
        (throw (error/InvalidGhSignature "No x-hub-signature found")))

      (let [[method gh-signature] (string/split xhub-header #"=")]
        (when (not= method "sha1")
          (throw (error/InvalidGhSignature "Wrong method used")))

        (when (not= gh-signature (.hmacHex codcheck-auth/gh-sha1-generator (:raw-body request)))
          (throw (error/InvalidGhSignature "Request signature does not match with API one")))

        (handler request)))))
