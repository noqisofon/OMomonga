(ns ^{:doc "ウィジェット用の低レベルな API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.widgets
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defprotocol Widget
  ""
  (dispose [this] "ウィジェットを破棄します。")
  (data [this] [this key] "ウィジェットに関連付けられたデータを取得します。")
  (display [this] "ウィジェットに関連付けられているディスプレイオブジェクトを返します。")
  (style [this] "ウィジェットに設定されているスタイルを返します。")
  (dispose? [this] "ウィジェットが破棄されていた際に真を返します。")
  (reskin [this flags] "スキンを変更します？")
  (set-data! [this a_data] [this key a_data] "ウィジェットにデータを関連付けます。")
  (widget? [this] "ウィジェットなら真を返します。"))


(defprotocol Control
  ""
  (compute-size [this width-hint height-hint] [this width-hint height-hint changed] "渡された幅と高さからコントロールのサイズを計算して返します。")
  (background-color [this] "コントロールの背景色を返します。")
  (background-image [this] "コントロールの背景イメージを返します。")
  (cursor [this] "カーソルオブジェクトを返します。")
  (border-width [this] "線の幅を返します。")
  (bounds [this] "コントロールの位置とサイズを表す rectangle 構造体を返します。")
  (font [this] "コントロールに設定されているフォントを返します。")
  (foreground-color [this] "コントロールの文字色を返します。")
  (layout-data [this] "コントロールに設定されているレイアウトデータを返します。"))


(extend-type org.eclipse.swt.widgets.Widget
  Widget
  (dispose [this]
    (.dispose this))

  (data [this]
    (.getData this))
  (data  [this key]
    (.getData this key))

  (display [this]
    (.getDisplay this))

  (style [this]
    (to-style-value (.getStyle this)))

  (dispose? [this]
    (.isDisposed this))

  (reskin [this flags]
    (.reskin this flags))

  (set-data! [this a_data]
    (.setData this a_data))
  (set-data! [this key a_data]
    (.setData this key a_data))

  (widget? [this]
     (instance? org.eclipse.swt.widgets.Widget this))

  Control
  (compute-size [this width-hint height-hint]
    (Point->size (.computeSize this width-hint height-hint)))
  (compute-size  [this width-hint height-hint changed]
    (Point->size (.computeSize this width-hint height-hint changed)))

  (background-color [this]
    (Color->color (.getBackgroundColor this)))

  (background-image [this]
    (.getBackgroundImage this))

  (cursor [this]
    (getCursor this))

  (border-width [this]
    (.getBorderWidth this))

  (bounds [this]
    (Rectangle->rectangle (.getBounds this)))

  (font [this]
    (.getFont this))

  (foreground-color [this]
    (.getForegroundColor this))

  (layout-data [this]
    (let [a_layout-data (.getLayoutData this)]
      (cond
       ;;(instance? org.eclipse.swt.layout.FormData a_layout-data) (to-form-data a_layout-data)
       (instance? org.eclipse.swt.layout.GridData a_layout-data) (to-grid-data a_layout-data)
       ;;(instance? org.eclipse.swt.layout.RowData a_layout-data) (to-row-layout a_layout-data)
       (:else a_layout-data)))))
