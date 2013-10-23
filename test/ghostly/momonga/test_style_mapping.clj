(ns ghostly.momonga.test_style_mapping
  (:require [clojure.test :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]))


(deftest style_mapping
  (testing "スタイル・キーワードの配列の要素が *-style-alist に含まれているか調べる"
      (is (every? #(contains? menu-style-alist %) [:bar]))))
