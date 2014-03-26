(ns toy-jvm.frame
  (:require [toy-jvm.util :as util]))

(def ^:dynamic *trace* false)

(defrecord Frame [pc locals stack program])

(defn modify [frame & {:as args}]
  (when *trace*
    (println (or (:pc args) (:pc frame))) (flush))
  (map->Frame (merge frame
                     {:pc (inc (:pc frame))}
                     args)))

(defn local-value [frame id]
  (get (:locals frame) id))

(defn next-inst [frame]
  (nth (:program frame) (:pc frame)))

(defn load-program [raw-code]
  (map->Frame {:pc 0 :locals {} :stack [] :program raw-code}))
