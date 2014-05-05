(defproject wheel "0.1.0-SNAPSHOT"
  :description "User management library."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[codox "0.7.2"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [com.h2database/h2 "1.4.177"]
                 [crypto-password "0.1.3"]])
