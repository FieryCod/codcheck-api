(ns codcheck.api.common.response
  (:require
   [ring.util.response :as ring-response]
   [clojure.string :as string]))

(defn- json-response
  "Respond with application/json header"
  [body status]
  (ring-response/content-type {:body body :status status} "application/json"))

(defn server-err
  "Respond with 500 status"
  [msg]
  (json-response {:message msg :status "InternalServerError"} 500))

(defn custom-err
  [msg ex-map]
  (json-response {:message msg :status (:status ex-map)} (:statusCode ex-map)))

(defn ok
  "Respond with 200 status"
  [body]
  (json-response body 200))

(defn success
  "Respond with success message"
  ([]
   (success ""))
  ([msg]
   (ok {:message (string/trim (str "Success! " msg))})))
