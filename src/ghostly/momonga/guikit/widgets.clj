(ns ^{:doc "ウィジェット用の低レベルな API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.events :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
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
  (set-data! [this a_data] [this key a_data] "ウィジェットにデータを関連付けます。")
  (widget? [this] "ウィジェットなら真を返します。"))


(extend-type org.eclipse.swt.widgets.Widget
  Widget
  (add-dispose-listener [this hook-fn]
    ;; hook-fn は何らかの関数である。
    (assert (fn? hook-fn))
    (let [a_dipose-listener (reify org.eclipse.swt.events.DisposeListener
                              (widgetDisposed [self a_dispose-event]
                                (hook-fn self (as-type-event a_dispose-event))))]
      (.addDisposeListener this a_dipose-listener)
      a_dipose-listener))

  (add-listener [this event-type hook-fn]
    ;; event-type はキーワードである。
    (assert (keyword? event-type))
    ;; hook-fn は何らかの関数である。
    (assert (fn? hook-fn))
    (let [an_listener (reify org.eclipse.swt.widgets.Listener
                        (handleEvent [this an_event]
                          (hook-fn this (as-event an_event))))
          swt-event-type (event-type-symbol-alist event-type)]
      (.addListener this swt-event-type an_listener)
      an_listener))

  (dispose [this]
    (.dispose this))

  (data [this]
    (.getData this))
  (data  [this key]
    (.getData this key))

  (display [this]
    (.getDisplay this))

  (listeners [this event-type]
    ;; event-type はキーワードである。
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.getListeners this swt-event-type)))

  (style [this]
    (to-style-value (.getStyle this)))

  (dispose? [this]
    (.isDisposed this))

  (listening? [this event-type]
    ;; event-type はキーワードである。
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.isListening this swt-event-type)))

  (notify-listeners [this event-type an_event]
    ;; event-type はキーワードである。
    (assert (keyword? event-type))
    (.notifyListener this (event-type-symbol-alist event-type) an_event))

  (remove-dispose-listener [this listener]
    (.removeDisposeListener this listener))

  (remove-listener [this event-type listener]
    ;; event-type はキーワードである。
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.removeListener this swt-event-type listener)))

  (reskin [this flags]
    (.reskin this flags))

  (set-data! [this a_data]
    (.setData this a_data))

  (set-data! [this key a_data]
    (.setData this key a_data))

  (widget? [this]
     (instance? org.eclipse.swt.widgets.Widget this)))
