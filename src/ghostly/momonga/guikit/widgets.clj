(ns ^{:doc "ウィジェット用の低レベルな API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets
  (:gen-class)
  ;; (:import (org.eclipse.swt SWT)
  ;;          (org.eclipse.swt.graphics Point)
  ;;          (org.eclipse.swt.widgets Display
  ;;                                   Shell
  ;;                                   Widget
  ;;                                   Control))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defprotocol Widget
  ""
  (add-dispose-listener [this hook-fn] "ウィジェットにディスポーズリスナーを追加します。")
  (add-listener [this event-type hook-fn] "ウィジェットにリスナーを追加します。")
  (dispose [this] "ウィジェットを破棄します。")
  (data [this] [this key] "ウィジェットに関連付けられたデータを取得します。")
  (display [this] "ウィジェットに関連付けられているディスプレイオブジェクトを返します。")
  (listeners [this event-type] "ウィジェットに関連付けられているリスナーの配列を返します。")
  (style [this] "ウィジェットに設定されているスタイルを返します。")
  (dispose? [this] "ウィジェットが破棄されていた際に真を返します。")
  (listening? [this event-type] "指定されたイベントタイプをウィジェットがリスニングしていたら真を返します。")
  (notify-listeners [this event-type an_event] "イベントタイプとイベントを指定してウィジェットに関連付けられているリスナーを実行させます。")
  (remove-dispose-listener [this listener] "ウィジェットに追加されたディスポーズリスナーを削除します。")
  (remove-listener [this event-type listener] "ウィジェットに追加されたリスナーを削除します。")
  (reskin [this flags] "スキンを変更します？")
  (set-data! [this a_data] [this key a_data] "ウィジェットにデータを関連付けます。"))


(extend-type org.eclipse.swt.widgets.Widget
  Widget
  (add-dispose-listener [this hook-fn]
    ;; hook-fn は何らかの関数である。
    (assert (fn? hook-fn))

    (let [a_dipose-listener (reify DisposeListener
                              (widgetDisposed [self a_dispose-event]
                                (hook-fn self (as-type-event a_dispose-event))))]
      (.addDisposeListener this a_dipose-listener)
      a_dipose-listener))
  (add-listener [this an_event-type hook-fn]
    ;; an_event-type はキーワードである。
    (assert (keyword? an_event-type))
    ;; hook-fn は何らかの関数である。
    (assert (fn? hook-fn))

    (let [an_listener (reify Listener
                        (handleEvent [this an_event]
                          (hook-fn this (as-event an_event))))
          swt-event-type (event-type-symbol-alist an_event-type)]
      (.addListener widget-or-control swt-event-type an_listener)
      an_listener))
  (dispose [this]
    (.dispose this))
  (data [this]
    (.getData this))
  (data  [this key]
    (.getData this key))
  (display [this]
    (.getDisplay this))
  (listeners [this an_event-type]
    ;; an_event-type はキーワードである。
    (assert (keyword? an_event-type))
    (let [swt-event-type (event-type-symbol-alist an_event-type)]
      (.getListeners this swt-event-type)))
  (style [this]
    (to-style-value (.getStyle this)))
  (dispose? [this]
    (isDisposed this))
  (listening? [this event-type]
    ;; an_event-type はキーワードである。
    (assert (keyword? an_event-type))
    (let [swt-event-type (event-type-symbol-alist an_event-type)]
      (.isListening widget-or-control swt-event-type)))
  (notify-listeners [this an_event-type an_event]
    ;; an_event-type はキーワードである。
    (assert (keyword? an_event-type))
    (.notifyListener this (event-type-symbol-alist an_event-type) an_event))
  (remove-dispose-listener [this listener]
    (.removeDisposeListener this listener))
  (remove-listener [this an_event-type listener]
    ;; an_event-type はキーワードである。
    (assert (keyword? an_event-type))
    (let [swt-event-type (event-type-symbol-alist an_event-type)]
      (.removeListener this swt-event-type listener)))
  (reskin [this flags]
    (.reskin this flags))
  (set-data! [this a_data]
    (.setData this a_data))
  (set-data! [this key a_data]
    (.setData this key a_data))


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


(defn ^{:requires-bindings true
        :category "accessing"
        :subcategory nil
        :added "0.1" }
  control-compute-size
  "コントロールのサイズを計算します。"
  ([a_control width-hint height-hint]
     (point->size (.computeSize a_control (int width-hint) (int height-hint))))
  ([a_control width-hint height-hint changed]
     (point->size (.computeSize a_control (int width-hint) (int height-hint) changed))))
