(ns ghostly.momonga.test_guikit_with_label
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Label))
  (:require [clojure.test :refer :all]
            [ghostly.momonga.macros :refer :all]
            [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit :refer :all]))


(deftest sketch_guikit_with_label
  (let [a_root-window (make-window              :title "Label Demo")
        a_label       (make-label a_root-window :text "Hello, World!")]
    ;; a_root-window は Shell のインスタンスである。
    (is (instance? Shell a_root-window))
    ;; a_root-window のタイトルの値は "Label Demo" である。
    (is (= "Label Demo" (.getText a_root-window)))

    ;; a_label は Label のインスタンスである。
    (is (instance? Label a_label))
    ;; a_label のテキストの値は "Hello, World!" である。
    (is (= "Hello, World!" (.getText a_label)))

    ;; #'root-window の戻り値と #'make-window の戻り値である a_root-window は同一である。
    (is (= (root-window) a_root-window))

    (window-pack-and-open a_root-window 300 100)

    (let [a_location (window-location a_root-window)]
      ;; a_location は nil ではない。
      (is (not (nil? a_location)))
      ;; (a_location :x) は正数である。
      (is (pos? (a_location :x)))
      ;; (a_location :y) は正数である。
      (is (pos? (a_location :y))))

    ;; a_root-window の幅は 300 である。
    (is (= 300 (window-width a_root-window)))
    ;; a_root-window の高さは 100 である。
    (is (= 100 (window-height a_root-window)))

    (main-loop a_root-window)

    ;; メインループが終了した後、ディスプレイオブジェクトは dispose される。
    (is (.isDisposed *display*))))
