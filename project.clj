(defproject codcheck-api "0.0.2"
  :description "API of codcheck application"

  :dependencies [[org.clojure/clojure "1.10.0-alpha6"]
                 [compojure "1.6.1"]
                 [org.clojure/core.async "0.4.474"]
                 [ring/ring-json "0.4.0"]
                 [cheshire "5.8.0"]
                 [clj-time "0.14.4"]
                 [codcheck "0.0.1"]
                 [ring/ring-defaults "0.3.2"]
                 [com.novemberain/langohr "5.0.0"]
                 [irresponsible/tentacles "0.6.2"]]

  :source-paths ["src"]

  :plugins [[lein-ring "0.12.4"]
            [lein-bikeshed "0.5.1"]
            [lein-kibit "0.1.6"]
            [lein-shell "0.5.0"]
            [jonase/eastwood "0.2.7"]]

  :ring {:handler codcheck-api.server/runnable-server
         :adapter {:min-threads 5 :max-threads 150}
         :async?  false}

  :aliases {"ci-check" ["do" ["kibit"] ["eastwood"] ["bikeshed"]]}

  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "4.0.1"]
                                  [ring/ring-mock "0.3.0"]]}})
