(ns codcheck.api.development.controller
  (:require
   [compojure.core :refer :all]
   [codcheck.envs :refer [envs]]
   [codcheck.api.development.middleware :refer [only-development]]
   [codcheck.api.common.helper :as helper]
   [codcheck.api.common.macro :as macro]
   [codcheck.api.common.response :as response]
   [codcheck.api.path :as path]
   [codcheck.auth]))

(defroutes development-controller
  (context path/development []

    (macro/bind-controller-middlewares

     [only-development]

     (GET "/signed-token" _
       (fn [_]
         (response/ok {:signed-token (codcheck.auth/gh-sign-token)})))

     (GET "/installation-access-token" _
       (fn [_]
         (let [signed-token (codcheck.auth/gh-sign-token)
               default-installation-id (:DEFAULT_INSTALLATION_ID envs)
               token (codcheck.auth/installation-request->token
                      (codcheck.auth/gh-installation-token-request default-installation-id signed-token))]
           (response/ok {:token token})))))))
