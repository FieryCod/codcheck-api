(ns codcheck.api.server
  (:require
   [codcheck logging rmq]
   [codcheck.api.application]))

(defonce runnable-server
  (do
    (codcheck.rmq/connect!)
    (codcheck.rmq/open-chan!)
    (codcheck.logging/setup-logging "codcheck-api")
    (codcheck.api.application/setup-app-handler)))
