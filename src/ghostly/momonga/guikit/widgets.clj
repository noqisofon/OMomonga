(ns ^{:doc "ウィジェット用の低レベルな API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events SelectionListener)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Label
                                    Link
                                    Text
                                    Button
                                    Widget
                                    Control))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Widget 関連
;;
(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  widget?
  "引数 window-or-widget が Widget のインスタンスならば真を返します。"
  [window-or-widget]
  (instance? Widget window-or-widget))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  control?
  "引数 widget-or-control が Control のインスタンスならば真を返します。"
  [widget-or-control]
  (instance? Control widget-or-control))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  window?
  "引数 widget-or-control が Shell のインスタンスならば真を返します。"
  [widget-or-control]
  (instance? Shell widget-or-control))


(defn 
  ^{:requires-bindings true
    :category "testing functionality"
    :subcategory "widget status"
    :added "0.1" }
  enabled?
  "引数 window-or-control が有効ならば真を返します。"
  [window-or-control]
  (.isEnabled window-or-control))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "widget status"
        :added "0.1" }
  dispose?
  "引数 display-or-widget が既に開放されたならば真を返します。"
  [display-or-widget]
  (.isDisposed display-or-widget))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  widget-data
  "引数 widget-or-control の中のデータを返します。"
  ([widget-or-control]
     (.getData widget-or-control))
  ([widget-or-control a_key]
     (.getData widget-or-control a_key)))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  widget-style
  "引数 widget-or-control のスタイルをキーワードの配列として返します。"
  [widget-or-control]
  (to-style-value (.getStyle widget-or-control)))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  widget-display
  "ウィジェットまたはコントロールが所属しているディスプレイオブジェクトを返します。"
  [widget-or-control]
  (.getDisplay widget-or-control))


(defn ^{:requires-bindings true
        :category "disposing"
        :subcategory nil
        :added "0.1" }
  widget-dispose
  "引数 widget-or-control を開放します。"
  [widget-or-control]
  (.dispose widget-or-control))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  widget-reskin
  "つーか、何すんのこれ……"
  [widget-or-control a_flags]
  (.reskin widget-or-control a_flags))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  set-widget-data!
  "引数 widget-or-control に a_key に対応した a_data、または a_data を設定します。"
  [widget-or-control a_data & [a_key]]
  (if a_key
    (.setData widget-or-control a_data)
    ;; else
    (.setData widget-or-control (str a_key) a_data)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Control 関連
;;
(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "widget status"
        :added "0.1" }
  focus-control?
  "引数 a_control のフォーカスが操作可能なら真を返します。"
  [a_control]
  (.isFocusControl a_control))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "widget status"
        :added "0.1" }
  reparentable?
  ""
  [a_control]
  (.isReparentable a_control))


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "widget status"
        :added "0.1" }
  visible?
  "引数 a_control が可視なら真を返します。"
  [a_control]
  (.isVisible a_control))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  control-font
  "コントロールのフォントを返します。"
  [a_control]
  (.getFont a_control))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  control-text
  "コントロールのテキストを返します。"
  [a_control]
  (.getText a_control))


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  control-parent
  "コントロールの親を返します。"
  [a_control]
  (.getParent a_control))
