(ns codcheck-api.common.helper)

(defmacro before-request
  [middlewares handler]
  `(-> ~handler ~@(reverse middlewares)))

(defn condp-some
  "Takes the test-expr-s sequence and
  returns true when any from test expresions is equal to expr
  otherwise returns false"
  [test-expr-s expr]
  (some #(= % expr) test-expr-s))
