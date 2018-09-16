(ns codcheck-api.common.helper
  (:require [compojure.core :as compojure]))

(defmacro before-request
  [middlewares handler]
  `(-> ~handler ~@(reverse middlewares)))

(defmacro before-all-requests
  [middlewares & handlers]
  `(before-request ~middlewares ~`(compojure/routes ~@handlers)))

(defn condp-some
  "Takes the test-expr-s sequence and
  returns true when any from test expresions is equal to expr
  otherwise returns false"
  [test-expr-s expr]
  (some #(= % expr) test-expr-s))
