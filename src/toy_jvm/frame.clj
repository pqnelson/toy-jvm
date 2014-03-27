(ns toy-jvm.frame
  (:require [toy-jvm.util :as util]))

(def ^:dynamic *trace* false)

(defrecord Frame [pc locals stack program])

(defn update-frame [frame updates]
  (when *trace*
    (println (:pc frame)) (flush))
  (map->Frame (merge frame {:pc (inc (:pc frame))} updates)))

(defn- no-changes? [args]
  (or (empty? args)
      (not args)
      (and (= 1 (count args))
           (contains? #{nil 'pass} (first args)))))

(defn modify [frame & {:as args}]
  (if (no-changes? args)
    frame
    (update-frame frame args)))

(defn local-value [frame id]
  (get-in frame [:locals id]))

(defn next-inst [frame]
  (nth (:program frame) (:pc frame)))

(defn load-program [raw-code]
  (map->Frame {:pc 0 :locals {} :stack [] :program raw-code}))
