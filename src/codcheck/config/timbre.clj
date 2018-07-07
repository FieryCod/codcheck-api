(ns codcheck.config.timbre
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]
            [ring.logger :as ring-logger])
  (:import [java.text SimpleDateFormat]
           [java.util Date]))

(defn log-file-name
  "Creates the name of .log file"
  [date]
  (let [formatter (SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss")]
    (str "logs/codcheck-api-" (.format formatter date) ".log")))

(defn setup-timbre
  "Setups the timbre logger"
  []
  (timbre/merge-config!
   {:appenders {:spit (appenders/spit-appender
                       {:fname (log-file-name (Date.))})}}))
