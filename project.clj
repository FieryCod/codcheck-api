(defproject codcheck "0.1.0-SNAPSHOT"
  :description "Validate your commit messages with ease"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/core.async "0.4.474"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring-logger "1.0.1"]]

  :source-paths ["src"] :plugins [[lein-ring "0.12.4"]
                                  [lein-bikeshed "0.5.1"]
                                  [lein-kibit "0.1.6"]
                                  [lein-shell "0.5.0"]
                                  [jonase/eastwood "0.2.7"]]

  :ring {:handler codcheck.server/runnable-server
         :adapter {:min-threads 5 :max-threads 150}
         :async?  false}

  :aliases {"ci-check" ["do" ["kibit"] ["eastwood"] ["bikeshed"]]}

  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "4.0.1"]
                                  [ring/ring-mock "0.3.0"]]

                   :aliases {"dc-up" ["do" ["shell"
                                            "docker-compose" "-f" "docker/docker-compose.development.yml" "up"]]
                             "dc-restart" ["do" ["shell"
                                                 "docker-compose" "-f" "docker/docker-compose.development.yml" "restart"]]}}})
