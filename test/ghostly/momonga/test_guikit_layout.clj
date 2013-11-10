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

  (testing "struct-map grid-layout で指定したキーワードに設定した値が入っていること"
    (let [a_grid-layout (struct-map grid-layout :num-columns 2 :make-columns-equal-width false)]
      (is (a_grid-layout :num-columns) 2)
      (is (false? (a_grid-layout :make-columns-equal-width)))))

  (testing "struct-map grid-layout で指定したキーワードに設定した値以外は nil であること"
    (let [a_grid-layout (struct-map grid-layout :num-columns 2 :make-columns-equal-width false)]
      (is (nil? (a_grid-layout :margin-bottom)))
      (is (nil? (a_grid-layout :margin-height)))
      (is (nil? (a_grid-layout :margin-left)))
      (is (nil? (a_grid-layout :margin-right)))
      (is (nil? (a_grid-layout :margin-top)))
      (is (nil? (a_grid-layout :margin-width)))))

  (testing "asGridLayout の戻り値の型は FillLayout である"
    (is (instance? GridLayout (asGridLayout (struct grid-layout))))))
