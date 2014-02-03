(ns ghostly.momonga.test_guikit_with_label
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.widgets Display))
  (:require [clojure.test :refer :all]
            [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.guikit.widgets.window :refer :all]
            [ghostly.momonga.guikit.widgets.display :refer :all]
            [ghostly.momonga.guikit.widgets.label :refer :all]
            [ghostly.momonga.guikit.widgets.decorations :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(deftest guikit_with_label
  (testing "窓を表示し、すぐに閉じる"
    (within-main-loop [display]
                      (let [shell (window display
                                          :title "Label Demo"
                                          :default-width 300
                                          :default-height 100)
                            a_label (label shell :text "Hello, World!")]
                        ;; shell は Shell のインスタンスである。
                        (is (window? shell))
                        ;; shell のタイトルの値は "Label Demo" である。
                        (is (= "Label Demo" (text shell)))

                        ;; a_label は Label のインスタンスである。
                        (is (label? a_label))
                        ;; a_label のテキストの値は "Hello, World!" である。
                        (is (= "Hello, World!" (text a_label)))

                        ;; root-window の戻り値と window の戻り値である shell は同一である。
                        (is (= (root-window display) shell))

                        ;; テストのため、すぐに窓を閉じるようにする。
                        (.timerExec display 1 (reify Runnable
                                                (run [this]
                                                  (.close shell))))))))
