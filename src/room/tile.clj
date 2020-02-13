(ns room.tile
  (:require [clojure.string :as str]))

(defn read-tileset [path]
  (let [tileset-string (slurp path)
        lines (str/split tileset-string #"\n")
        parse-line (fn [line]
                     (let [fields (str/split line #" ")]
                       {:character (first (first fields))
                        :red (Integer/parseInt (nth fields 1))
                        :green (Integer/parseInt (nth fields 2))
                        :blue (Integer/parseInt (nth fields 3))}))
       ]
    (map parse-line lines)))

(defn read-tilemap [path]
  (let [tilemap-string (slurp path)
        lines (str/split tilemap-string #"\n")
        rows (count lines)
        cols (count (first lines))
        ]
    (for [row (range rows)]
      (for [col (range cols)]
        (nth (nth lines row) col)))))


(defn find-walls [tilemap]
  "Returns a map of north-south running walls and east-west running walls."
  ; For an MxN tilemap, north-south is Mx(N+1) and east-west is (M+1)xN
  ; flag a wall if (the tiles on either side are (not both #) or (both not #)) or (the tile is not # and at the boundary of the map)
  (let [
        ; first, find interior e-w walls
        interior-ew (for [row (range (dec (count tilemap)))]
                      (for [col (range (count (first tilemap)))]
                        (let [this-tile (nth (nth tilemap row) col)
                              south-tile (nth (nth tilemap (inc row)) col)]
                          (or
                            (and (= this-tile \#) (not= south-tile \#))
                            (and (not= this-tile \#) (= south-tile \#))))))
        ; find north exterior e-w walls
        north-exterior-ew (map #(not= % \#) (first tilemap))
        ; find south exterior e-w walls
        south-exterior-ew (map #(not= % \#) (last tilemap))

        ; find interior n-s walls
        interior-ns (for [row (range (count tilemap))]
                      (for [col (range (dec (count (first tilemap))))]
                        (let [this-tile (nth (nth tilemap row) col)
                              east-tile (nth (nth tilemap row) (inc col))]
                          (or
                            (and (= this-tile \#) (not= east-tile \#))
                            (and (not= this-tile \#) (= east-tile \#))))))
        ; find west exterior n-s walls
        west-exterior-ns (map #(not= (first %) \#) tilemap)
        ; find east exterior n-s walls
        east-exterior-ns (map #(not= (last %) \#) tilemap)
        ; combine ns lists
        north-south (for [i (range (count tilemap))]
                      (concat (list (nth west-exterior-ns i))
                              (nth interior-ns i)
                              (list (nth east-exterior-ns i))))
        dummy (println (str "west exterior ns" (into [] west-exterior-ns)))
        ]
    {:north-south north-south
     :east-west (concat (list north-exterior-ew) interior-ew (list south-exterior-ew))}
  ))
