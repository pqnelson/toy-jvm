(ns toy-jvm.util
  (:refer-clojure :exclude [pop]))

(defn third [coll]
  (second (next coll)))

(defn fourth [coll]
  (third (next coll)))

(def op-code first)
(def arg1    second)
(def arg2    third)
(def arg3    fourth)

(defmacro dbg [x]
  `(let [x# ~x]
     (println '~x "=" x#)
     x#))

;; stack related code
(defn push [obj stack]
  (cons obj stack))

(defn top [stack]
  (first stack))

(defn pop [stack]
  (when (seq stack)
    (rest stack)))
