(ns codcheck-api.development.controller
  (:require
   [compojure.core :as compojure]
   [cheshire.core :as cheshire]
   [codcheck.envs :refer [envs]]
   [codcheck-api.development.middleware :as development-middleware]
   [codcheck-api.common.helper :as common-helper]
   [codcheck-api.common.response :as common-response]
   [codcheck-api.consts.path :as consts-path]
   [codcheck.auth :as codcheck-auth]))

(compojure/defroutes development-controller
  (compojure/context consts-path/development []

    (common-helper/before-all-requests
     [development-middleware/only-development]

     (compojure/GET "/signed-token" _
       (fn [_]
         (common-response/ok {:signed-token (codcheck-auth/gh-sign-token)})))

     (compojure/GET "/installation-access-token" _
       (fn [_]
         (let [installation-request (codcheck-auth/gh-installation-token
                                     (:default-installation-id envs)
                                     (codcheck-auth/gh-sign-token))
               parsed-body (cheshire/parse-string (:body installation-request) true)
               token (get parsed-body :token "")]
           (common-response/ok {:token token})))))))
