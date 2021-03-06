(ns ^{:doc "ラベル用の API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets.label
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events SelectionListener)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Label))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  label?
  "引数 label-or-control がラベルなら真を返します。"
  [label-or-control]
  (instance? Label label-or-control))


(defn ^{:requires-bindings true
        :category "instance creation"
        :subcategory nil
        :added "0.1" }
  label
  "親窓を受け取り、ラベルオブジェクトを作成して返します。"
  [widget-or-window & {a_text :text a_styles :style a_grid-data :layout-data}]
  (let [swt-style-value (to-swt-style-value label-style-alist a_styles)
        a_label (Label. widget-or-window swt-style-value)]
    (if a_text
      (.setText a_label a_text))
    a_label))
