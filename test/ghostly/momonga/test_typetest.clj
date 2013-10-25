(ns ghostly.momonga.test_typetest
  (:require [clojure.test :refer :all]))


(deftest test_typetest
  (testing "Control は Widget の派生クラスであること"
      (is (isa? org.eclipse.swt.widgets.Control org.eclipse.swt.widgets.Widget)))

  (testing "Button は Widget の派生クラスであること"
      (is (isa? org.eclipse.swt.widgets.Button org.eclipse.swt.widgets.Widget)))

  (testing "Shell は Widget や Controll のインスタンスであること"
      (let [shell (org.eclipse.swt.widgets.Shell.)]
        (is (instance? org.eclipse.swt.widgets.Control shell))
        (is (instance? org.eclipse.swt.widgets.Widget shell)))))

