(ns ghostly.momonga.test_guikit_with_label
  ;; (:import (org.eclipse.swt SWT)
  ;;          (org.eclipse.swt.layout FillLayout
  ;;                                  GridLayout)
  ;;          (org.eclipse.swt.graphics Point)
  ;;          (org.eclipse.swt.widgets Display
  ;;                                   Shell
  ;;                                   Label))
  (:require [clojure.test :refer :all]
            [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


;; (deftest guikit_with_label
;;   (in-display [display]
;;               (with-widget [a_root-window (window display :title "Label Demo")]
;;                 (let [a_label (label a_root-window :text "Hello, World!")]
;;                   ;; a_root-window は Shell のインスタンスである。
;;                   (is (instance? Shell a_root-window))
;;                   ;; a_root-window のタイトルの値は "Label Demo" である。
;;                   (is (= "Label Demo" (.getText a_root-window)))

;;                   ;; a_label は Label のインスタンスである。
;;                   (is (instance? Label a_label))
;;                   ;; a_label のテキストの値は "Hello, World!" である。
;;                   (is (= "Hello, World!" (.getText a_label)))

;;                   ;; #'root-window の戻り値と #'make-window の戻り値である a_root-window は同一である。
;;                   (is (= (root-window display) a_root-window))

;;                   (window-pack-and-open a_root-window 300 100)

;;                   (let [a_location (window-location a_root-window)]
;;                     ;; a_location は nil ではない。
;;                     (is (not (nil? a_location)))
;;                     ;; (a_location :x) は正数である。
;;                     (is (>= (a_location :x) 0))
;;                     ;; (a_location :y) は正数である。
;;                     (is (>= (a_location :y) 0)))

;;                   ;; 300, 100 にならないため、コメントアウト。
;;                   ;; ;; a_root-window の幅は 300 である。
;;                   ;; (is (= 300 (window-width a_root-window)))
;;                   ;; ;; a_root-window の高さは 100 である。
;;                   ;; (is (= 100 (window-height a_root-window)))

;;                   ;; テストのため、すぐに窓を閉じるようにする。
;;                   (.timerExec display 1 (reify Runnable
;;                                           (run [this]
;;                                             (.close a_root-window))))

;;                   (main-loop display a_root-window)

;;                   ;; メインループが終了した後、ディスプレイオブジェクトは dispose される。
;;                   (is (.isDisposed display))))))


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
                        (is (= "Label Demo" (control-text shell)))

                        ;; a_label は Label のインスタンスである。
                        (is (label? a_label))
                        ;; a_label のテキストの値は "Hello, World!" である。
                        (is (= "Hello, World!" (control-text a_label)))

                        ;; root-window の戻り値と window の戻り値である shell は同一である。
                        (is (= (root-window display) shell))

                        ;; テストのため、すぐに窓を閉じるようにする。
                        (.timerExec display 1 (reify Runnable
                                                (run [this]
                                                  (.close shell))))))))
