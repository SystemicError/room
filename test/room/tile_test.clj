(ns room.tile-test
  (:require [clojure.test :refer :all]
            [room.tile :refer :all]))

(deftest read-tileset-test
  (testing "read-tileset test fail."
    (is (= (read-tileset "tileset")
           (list {:character \a :red 255 :green 0 :blue 0}
                 {:character \b :red 0 :green 255 :blue 0}
                 {:character \c :red 0 :green 0 :blue 255})))
    ))

(deftest read-tilemap-test
  (testing "read-tilemap test fail."
    (is (= (read-tilemap "tilemap")
           (list (list \# \# \# \a)
                 (list \b \a \b \c)
                 (list \a \# \b \#)
                 (list \# \c \c \c))))
    ))

(deftest find-walls-test
  (testing "find-walls test fail."
    (is (= (find-walls (read-tilemap "tilemap"))
           {:north-south (list (list false false false true true)
                               (list true false false false true)
                               (list true true true true false)
                               (list false true false false true))
            :east-west (list (list false false false true)
                             (list true true true false)
                             (list false true false true)
                             (list true true false true)
                             (list false true true true))}))
    ))
