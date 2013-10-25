(ns ghostly.momonga.test_typetest
  (:import (org.eclipse.swt.widgets Widget
                                    Control
                                    Button
                                    Shell)
  (:require [clojure.test :refer :all]))


(deftest test_typetest
  (testing "Control は Widget の派生クラスであること"
      (is (isa? Control Widget)))

  (testing "Button は Widget の派生クラスであること"
      (is (isa? Button Widget)))

  (testing "Shell は Widget や Controll のインスタンスであること"
      (let [shell ( Shell.)]
        (is (instance? Control shell))
        (is (instance? Widget shell)))))

