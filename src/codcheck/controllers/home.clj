(ns codcheck.controllers.home
  (:require [compojure.core :refer :all]
            [compojure.route :refer [not-found]]))

(defroutes home-route
  (GET "/" [] "Application is working...")
  (GET "/favicon.ico" [] (not-found "no favicon")))
