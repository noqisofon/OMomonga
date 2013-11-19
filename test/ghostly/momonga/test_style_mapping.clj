(ns ghostly.momonga.test_style_mapping
  (:import (org.eclipse.swt SWT))
  (:require [clojure.test :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]))


(deftest style_mapping
  (testing "スタイル・キーワードの配列の要素が control-style-alist に含まれていること"
    (is (every? #(contains? control-style-alist %) [:right-to-left])))

  (testing "control-style-alist の要素が scrollable-style-alist に含まれていること"
    (is (every? #(contains? button-style-alist %) (keys control-style-alist))))

  (testing "control-style-alist の要素が button-style-alist に含まれていること"
    (is (every? #(contains? button-style-alist %) (keys control-style-alist))))

  (testing "control-style-alist の要素が label-style-alist に含まれていること"
    (is (every? #(contains? label-style-alist %) (keys control-style-alist))))

  (testing "control-style-alist の要素が progress-bar-style-alist に含まれていること"
    (is (every? #(contains? progress-bar-style-alist %) (keys control-style-alist))))

  (testing "SWT/H_SCROLL と (scrollable-style-alist :h-scroll) の値が等しいこと"
    (is (= SWT/H_SCROLL (scrollable-style-alist :h-scroll))))

  (testing "SWT の定数を bit-or したものと、to-swt-style-value 関数の戻り値が等しいこと"
    (is (= (bit-or SWT/H_SCROLL SWT/V_SCROLL)
           (reduce bit-or (filter #(not (nil? %)) (map scrollable-style-alist [:h-scroll :v-scroll]))))))

  (testing "to-style-value が成功すること"

      (is [:no-background :no-focus :no-redraw-resize :no-merge-paints :no-radio-group]
          (to-style-value composite-style-alist (bit-or SWT/NO_BACKGROUND SWT/NO_FOCUS SWT/NO_REDRAW_RESIZE SWT/NO_MERGE_PAINTS SWT/NO_RADIO_GROUP)))))
