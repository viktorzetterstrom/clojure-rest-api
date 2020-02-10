(defproject clojure-rest-api "0.1.0-SNAPSHOT"
  :description "Clojure experimenting with restful api"
  :url ""
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"] ; Routing library
                 [http-kit "2.3.0"] ; Http library for client/server
                 [ring/ring-defaults "0.3.2"] ; Query params etc
                 [org.clojure/data.json "0.2.6"]] ; Clojure JSON library
  :main ^:skip-aot clojure-rest-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
