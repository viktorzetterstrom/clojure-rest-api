(ns clojure-rest-api.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class)) <

(defn simple-body-page [reg]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, World!"})

(defn request-example [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->>
          (pp/pprint req)
          (str "Request Object: " req))})

(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/request" [] request-example)
  (route/not-found "Error, page not found!"))

(defn -main
  "Main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; run server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ; Run server without ring defaults
    ; (server/run-server #'app-routes {:port port}) 
    (println (str "Running webserver at http://127.0.1.1:" port "/"))))

