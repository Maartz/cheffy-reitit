(ns cheffy-reitit.server
  (:require
   [ring.adapter.jetty :as jetty]
   [integrant.core :as ig]
   [environ.core :refer [env]]
   [cheffy-reitit.router :as router])
  (:gen-class))

(defn app
  [env]
  (router/routes env))

(defmethod ig/prep-key :server/jetty
  [_ config]
  (merge config {:port (Integer/parseInt (env :port))}))

(defmethod ig/prep-key :db/postgres
  [_ config]
  (merge config {:jdbc-url (env :jdbc-url)}))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (println (str "🚀 Server running on port: " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :cheffy-reitit/app
  [_ config]
  (println "\nStarted app")
  (app config))

(defmethod ig/init-key :db/postgres
  [_ config]
  (println "\nConfigured DB")
  (:jdbc-url config))

(defmethod ig/halt-key! :server/jetty
  [_ jetty]
  (.stop jetty))

(defn -main
  [config-file]
  (let [config (-> config-file slurp ig/read-string)]
    (-> config ig/prep ig/init)))

