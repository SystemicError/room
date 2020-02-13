(ns room.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.string :as str]
            [room.tile :as tile]))

(defn setup []
  (let [tileset (tile/read-tileset "tileset")
        tilemap (tile/read-tilemap "tilemap")
        walls (tile/find-walls tilemap)]
    ; Set frame rate to 30 frames per second.
    (q/frame-rate 30)
    {:tileset tileset
     :tilemap tilemap
     :walls walls
     :x 0.5 ; each tile is one unit square
     :y 0.5
     :direction 0.0}))

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
