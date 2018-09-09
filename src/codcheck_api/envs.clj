(ns codcheck-api.envs)

(def envs
  (read-string
    (slurp
     (str ".env."
          (or (System/getenv "ENVIRONMENT") "development")
          ".edn"))))
