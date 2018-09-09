(ns codcheck-api.logging.setup
  (:require
   [taoensso.timbre :as timbre]
   [taoensso.timbre.appenders.core :as timbre-appenders])
  (:import
   [java.text SimpleDateFormat]
   [java.util Date]))

(defn- remove-logging-ns-info
  [data]
  (if (= (:?ns-str data) "codcheck-api.logging.middleware")
    (-> data
       (assoc :?ns-str "")
       (assoc :?line ""))
    data))

(defn log-file-name
  "Creates the name of .log file"
  [date]
  (let [formatter (SimpleDateFormat. "yyyy-MM-dd'T'HH:mm:ss")]
    (str "logs/codcheck-api-api-" (.format formatter date) ".log")))

(defn setup-timbre
  "Setups the timbre logger"
  []
  (timbre/merge-config!
   {:appenders {:spit (timbre-appenders/spit-appender {:fname (log-file-name (Date.))})}
    :middleware [remove-logging-ns-info]}))
