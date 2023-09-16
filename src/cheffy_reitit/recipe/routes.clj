(ns cheffy-reitit.recipe.routes
  (:require [cheffy-reitit.recipe.handlers :as handlers]))

(defn routes
  [env]
  (let [db (:jdbc-url env)]
    ["/recipes" {:swagger {:tags ["recipes"]}
                 :get {:handler (handlers/list-all-recipes db)
                       :summary "List all recipes"}}]))
