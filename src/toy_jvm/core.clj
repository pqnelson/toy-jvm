(ns toy-jvm.core
  (:require (toy-jvm [frame :as frame]
                     [instruction :refer :all]
                     [util :as util]))
  (:gen-class))

(defn step [frame]
  (eval-op (frame/next-inst frame) frame))

(defn run [frame n]
  (loop [state frame
         k n]
    (if-not (pos? k)
      state
      (recur (step state) (dec k)))))

(util/defcode *pi*
  ((iconst 0)   ;  0
   (istore 1)   ;  1  j = 0
   (iconst 1)   ;  2
   (istore 2)   ;  3  k = 1
   (iload 0)    ;  4 loop:
   (ifeq 16)    ;  5  if n=0, goto exitj
   (iload 0)    ;  6
   (iconst 1)   ;  7
   (isub)       ;  8
   (ifeq 14)    ;  9  if n=1, goto exitk
   (iload 0)    ; 10
   (iconst 1)   ; 11
   (isub)       ; 12
   (istore 0)   ; 13  n=n-1
   (iload 2)    ; 14  save k on stack
   (iload 1)    ; 15  
   (iload 2)    ; 16
   (iadd)       ; 17
   (istore 2)   ; 18  k=j+k
   (istore 1)   ; 19  j= saved k
   (goto -16)   ; 20  goto loop
   (iload 1)    ; 21 exitj: return j
   (halt)       ; 22
   (iload 2)    ; 23 exitk: return k
   (halt)))     ; 24

(defn run-pi-program [x n]
  (run (frame/load-program (concat (list (list 'iconst x)
                                         '(istore 0))
                                   *pi*))
       n))

(defn -main [& args]
  (println (iconst? 'IcOnst))
  (println (run-pi-program 2 20))
  (println "Hello, World!"))
