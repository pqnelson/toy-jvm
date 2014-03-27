(ns toy-jvm.instruction
  (:refer-clojure :exclude [pop])
  (:require [clojure.string :as string]
            [toy-jvm.util :refer :all]
            [toy-jvm.frame :as frame]))

(defmacro defop [name args & changes]
  `(let [name# ~(str name)]
     (defn ~(symbol (str 'eval- name)) [~@args]
       (frame/modify ~(last args) ~@changes))
     (defn ~(symbol (str name "?")) [op-code#]
       (= (symbol (string/lower-case (str op-code#)))
          (symbol name#)))))

(defop iload [inst frame]
  :stack (push (frame/local-value frame (arg1 inst))
               (:stack frame)))

(defop iconst [inst frame]
  :stack (push (arg1 inst)
               (:stack frame)))

(defop iadd [inst frame]
  :stack (push (+ (top (:stack frame))
                  (top (pop (:stack frame))))
               (pop (pop (:stack frame)))))

(defop isub [inst frame]
  :stack (push (- (top (:stack frame))
                  (top (pop (:stack frame))))
               (pop (pop (:stack frame)))))

(defop imul [inst frame]
  :stack (push (* (top (:stack frame))
                  (top (pop (:stack frame))))
               (pop (pop (:stack frame)))))

(defop istore [inst frame]
  :locals (assoc (:locals frame) (arg1 inst) (top (:stack frame)))
  :stack (pop (:stack frame)))

(defop goto [inst frame]
  :pc (+ (arg1 inst) (:pc frame)))

(defop ifeq [inst frame]
  :pc (if (zero? (top (:stack frame)))
        (+ (arg1 inst) (:pc frame))
        (inc (:pc frame)))
  :stack (pop (:stack frame)))

(defop halt [inst frame]
  :pc (:pc frame))

(defn eval-op [inst env]
  (cond
   (iload? (op-code inst)) (eval-iload inst env)
   (iconst? (op-code inst)) (eval-iconst inst env)
   (iadd? (op-code inst)) (eval-iadd inst env)
   (isub? (op-code inst)) (eval-isub inst env)
   (imul? (op-code inst)) (eval-imul inst env)
   (istore? (op-code inst)) (eval-istore inst env)
   (goto? (op-code inst)) (eval-goto inst env)
   (ifeq? (op-code inst)) (eval-ifeq inst env)
   :else (eval-halt inst env)))
   :else          (throw (Exception. (str "Unknown opcode " (op-code inst))))))
