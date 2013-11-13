(ns ^{:doc "窓用の API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets.shell
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events SelectionListener)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.widgets.styles :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  window?
  "引数 widget-or-control が Shell のインスタンスならば真を返します。"
  [widget-or-control]
  (instance? Shell widget-or-control))


(defn ^{:requires-bindings true
        :category "instance creation"
        :subcategory nil
        :added "0.1" }
  window
  "シェルオブジェクトを作成します。"
  [a_display & {a_parent :parent a_title :title a_style :size a_size :style a_layout :layout}]
  (let [result-window (if a_parent
                        (if a_style
                          (Shell. a_parent (to-swt-style-value shell-style-alist a_style))
                          ;; else
                          (Shell. a_parent))
                        ;; else
                        (if a_style
                          (Shell. a_display (to-swt-style-value shell-style-alist a_style))
                          ;; else
                          (Shell. a_display)))]
    (if a_title
      (.setText result-window a_title))
    (if a_size
      (.setMinimumSize (asPoint a_size)))
    (if a_layout
      (cond (grid-layout? a_layout) (.setLayout result-window (asGridLayout a_layout))
            (fill-layout? a_layout) (.setLayout result-window (asFillLayout a_layout))
            :else (.setLayout result-window (FillLayout.))))
    result-window))
