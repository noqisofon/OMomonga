(ns ghostly.momonga.test_struct_mapping
  (:require [clojure.test :refer :all]
            [ghostly.momonga.twitter :refer :all]))


(deftest struct_mapping
  (testing "struct にメンバ用の引数を与えない場合、値は nil になる。"
    (let [a_paging (struct paging)]
      (is (every? nil? (map a_paging a_paging))))))
