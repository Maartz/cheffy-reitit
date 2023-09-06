(ns user
  (:require [integrant.core :as ig]
            [integrant.repl :as ig-repl]
            [integrant.repl.state :as state]
            [cheffy-reitit.server]))

(ig-repl/set-prep!
 (fn [] (-> "resources/config.edn" slurp ig/read-string)))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(defn app [] (-> state/system :cheffy-reitit/app))
(defn db [] (-> state/system :db/postgres))

(comment
    (go)
    (halt)
    (reset)
    (reset-all)
    (app)
    (db)
  )