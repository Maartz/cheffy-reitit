(ns cheffy-reitit.router
  [:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [cheffy-reitit.recipe.routes :as recipe]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            ])

(def router-config
  {:data {:muuntaja   m/instance
          :middleware [swagger/swagger-feature
                       muuntaja/format-middleware]}}
  )


(def swagger-docs
  ["/swagger.json" {:get
                    {:no-doc  true
                     :swagger {:basePath "/"
                               :info     {:title       "Cheffy API"
                                          :version     "1.0.0"
                                          :description "Cheffy API is organized around REST. Our API has predictable resource-oriented URLs, accepts form-encoded request bodies, returns JSON-encoded responses, Transit (msgpack json), or EDN"
                                          }
                               }
                     :handler (swagger/create-swagger-handler)
                     }}])
(defn routes
  [env]
  (ring/ring-handler
    (ring/router
      [swagger-docs
       ["/v1" (recipe/routes env)]
       ]
      router-config)
    (ring/routes
      (swagger-ui/create-swagger-ui-handler {:path "/"}))))
