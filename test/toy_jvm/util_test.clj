(ns toy-jvm.util-test
  (:refer-clojure :exclude [pop])
  (:require [clojure.test :refer :all]
            [toy-jvm.util :refer :all]))

(deftest third-tests
  (is (= (third [0 1 2 3 4 5]) 2))
  (is (= (third [1 2 3]) 3))
  (is (nil? (third [1 2])))
  (is (nil? (third [1])))
  (is (nil? (third [])))
  (is (nil? (third nil))))
  
(deftest fourth-tests
  (is (= (fourth [0 1 2 3 4 5]) 3))
  (is (nil? (fourth [1 2 3])))
  (is (nil? (fourth [1 2])))
  (is (nil? (fourth [1])))
  (is (nil? (fourth [])))
  (is (nil? (fourth nil))))
