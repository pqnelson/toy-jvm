(ns toy-jvm.instruction-test
  (:refer-clojure :exclude [pop])
  (:require [clojure.test :refer :all]
            [toy-jvm.instruction :refer :all]
            [toy-jvm.frame :as frame]))

(deftest iconst-test
  (is (= (eval-iconst '(iconst 42) {:pc 4 :locals {} :stack [] :program []})
         (frame/map->Frame {:pc 5 :locals {} :stack [42] :program []}))))

(deftest iload-test
  (is (= (eval-iload '(iload 42) {:pc 4 :locals {42 -1} :stack [] :program []})
         (frame/map->Frame {:pc 5 :locals {42 -1} :stack [-1] :program []}))))

(deftest iadd-test
  (is (= (eval-iadd '(iadd) {:pc 4
                             :locals {}
                             :stack [7 12]
                             :program '((iadd))})
         (frame/map->Frame {:pc 5 :locals {} :stack [19] :program '((iadd))}))))

(deftest isub-test
  (is (= (eval-isub '(isub) {:pc 4
                             :locals {}
                             :stack [7 12]
                             :program '((isub))})
         (frame/map->Frame {:pc 5 :locals {} :stack [-5] :program '((isub))}))))

(deftest imul-test
  (is (= (eval-imul '(imul) {:pc 4
                             :locals {}
                             :stack [7 12]
                             :program '((imul))})
         (frame/map->Frame {:pc 5 :locals {} :stack [84] :program '((imul))}))))

(deftest istore-test
  (is (= (eval-istore '(istore 5) {:pc 3
                                   :locals {}
                                   :stack [999]
                                   :program '((istore 5))})
         (frame/map->Frame {:pc 4 :locals {5 999} :stack []
                            :program '((istore 5))}))))

(deftest goto-test
  (is (= (eval-goto '(goto 10) {:pc 5
                                :locals {}
                                :stack []
                                :program '((goto 10))})
         (frame/map->Frame {:pc 15
                            :locals {}
                            :stack []
                            :program '((goto 10))}))))

(deftest ifeq-test
  (is (= (eval-ifeq '(ifeq 88)
                    {:pc 5
                     :locals {}
                     :stack [0]
                     :program '((ifeq 88))})
         (frame/map->Frame {:pc 93
                            :locals {}
                            :stack []
                            :program '((ifeq 88))}))))
(deftest halt-test
  (is (= (eval-halt '(halt)
                    {:pc 423
                     :locals {}
                     :stack []
                     :program []})
         (frame/map->Frame {:pc 423
                            :locals {}
                            :stack []
                            :program []}))))
