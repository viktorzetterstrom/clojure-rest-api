(ns clojure-rest-api.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
            [clojure.data.json :as json])
  (:gen-class))

; Helper to get parameter from :params in req
(defn get-parameter [req pname] (get (:params req) pname))

(defn simple-body-page [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, World!"})

(defn request-example [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->>
          (pp/pprint req)
          (str "Request Object: " req))})

(defn hello-name [req] ;(3)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->
          (pp/pprint req)
          (str "Hello " (get-parameter req :name)))})

; my people-collection mutable collection vector
(def people-collection (atom []))

; collection helper function that adds new person to people-collection
(defn add-person [firstname surname]
  (swap! people-collection
         conj {:firstname (str/capitalize firstname)
               :surname (str/capitalize surname)}))

; add dummy data
(add-person "Functional" "Human")
(add-person "Micky" "Mouse")

(defn people-handler [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (str (json/write-str @people-collection))})

(defn add-person-handler [req]
  {:status 200
   :headers {"Content-Type" "text/json"}
   :body (-> (let [p (partial get-parameter req)])
             (str (json/write-str (add-person (p :firstname) (p :surname)))))})

(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/request" [] request-example)
  (GET "/hello" [] hello-name)
  (GET "/people" [] people-handler)
  (GET "/people" [] add-person-handler)
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

