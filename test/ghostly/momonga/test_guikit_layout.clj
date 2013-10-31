(ns ghostly.momonga.test_guikit_layout
  (:import (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData))
  (:require [clojure.test :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]))


(deftest fill-layout-test
  (testing "struct fill-layout のインスタンスを fill-layout? に渡すと真になること"
    (is (fill-layout? (struct fill-layout))))

  (testing "asFillLayout の戻り値の型は FillLayout である"
    (is (instance? FillLayout (asFillLayout (struct fill-layout))))))


(deftest grid-layout-test
  (testing "struct grid-layout のインスタンスを grid-layout? に渡すと真になること"
    (is (grid-layout? (struct grid-layout))))

  (testing "asGridLayout の戻り値の型は FillLayout である"
    (is (instance? GridLayout (asGridLayout (struct grid-layout))))))
