(ns codcheck.api.common.macro
  (:require
   [compojure.core]))

(defmacro bind-request-middlewares
  [middlewares handler]
  `(-> ~handler ~@(reverse middlewares)))

(defmacro bind-controller-middlewares
  [middlewares & handlers]
  `(bind-request-middlewares ~middlewares ~`(compojure.core/routes ~@handlers)))
