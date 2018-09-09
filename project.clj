(defproject codcheck-api "0.0.2"
  :description "API of codcheck application"

  :dependencies [[org.clojure/clojure "1.10.0-alpha7"]
                 [compojure "1.6.1"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.clojure/core.async "0.4.474"]
                 [ring/ring-json "0.4.0"]
                 [clj-time "0.14.4"]
                 [buddy/buddy-core "1.5.0"]
                 [buddy/buddy-auth "2.1.0"]
                 [commons-codec/commons-codec "1.11"]
                 [ring/ring-defaults "0.3.2"]
                 [org.slf4j/slf4j-simple "1.8.0-beta2"]
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
