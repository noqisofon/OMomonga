(ns ^{:doc "ボタン用の API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets.button
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events SelectionListener)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Button))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.widgets.styles :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  button?
  "引数 button-or-control がボタンなら真を返します。"
  [button-or-control]
  (instance? Button button-or-control))


(defn ^{:requires-bindings true
        :category "instance creation"
        :subcategory nil
        :added "0.1" }
  button
  "親窓を受け取り、ボタンオブジェクトを作成して返します。"
  [parent-window & {a_label :label a_text :text a_layout-data :layout-data}]
  (let [result-button (Button. parent-window SWT/NULL)]
    (if a_label
      (do
        (assert (string? a_label))
        (.setText result-button a_label)))
    (if a_text
      (do
        (assert (string? a_text))
        (.setText result-button a_text)))
    (if a_layout-data
      (cond
       (grid-data? a_layout-data) (.setLayoutData (asGridData a_layout-data))
       :else nil))
    (if a_label
      (.setText result-button a_label))
    result-button))
