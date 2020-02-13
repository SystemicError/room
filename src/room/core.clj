(ns room.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.string :as str]
            [room.tile :as tile]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  {:tileset (read-tileset "tileset")
   :tilemap (read-tilemap "tilemap")})

(defn update-state [state]
  state)

(defn draw-state [state]
  (let [dummy (println (str "tileset " (:tileset state) "\n"
                            "tilemap " (:tilemap state) "\n"))
        ]
    ))

(q/defsketch room
  :title "ROOM"
  :size [640 480]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
