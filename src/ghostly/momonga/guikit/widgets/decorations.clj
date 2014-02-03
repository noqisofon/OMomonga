(ns ^{:doc ""
      :author "ned rihine" }
  ghostly.momonga.guikit.widgets.decorations
  (:gen-class)
  (:import (org.eclipse.swt SWT))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))

(defprotocol Decoration
  ""
  (compute-trim [this x y width height] "ウィジェットの大きさをいい感じにトリミングします。")
  (client-area [this] "ウィジェットのクライアントエリアの矩形を返します。")
  (default-button [this] "既定のボタンを返します。")
  (image [this] "ウィジェットに設定されているイメージを返します。")
  (images [this] "ウィジェットに設定されているイメージの配列を返します。")
  (location [this] "ウィジェットの位置を返します。")
  (maximumed? [this] "最大化していたら真を返します。")
  (menu-bar [this] "ウィジェットに設定されているメニューバーを返します。")
  (minimized? [this] "最小化していたら真を返します。")
  (text [this] "ウィジェットに設定されているテキストを返します。")
  (reparentable? [this] "")
  (default-button! [this button] "ウィジェットに既定のボタンを設定します。"))

(extend-type org.eclipse.swt.widgets.Decorations
  Decoration
  (compute-trim [this x y width height]
    "ウィジェットの大きさをいい感じにトリミングします。"
    (Rectangle->rectangle (.computeTrim this x y width height)))

  (client-area [this]
    "ウィジェットのクライアントエリアの矩形を返します。"
    (Rectangle->rectangle (.getClientArea this)))

  (default-button [this]
    "既定のボタンを返します。"
    (.getDefaultButton this))

  (image [this]
    "ウィジェットに設定されているイメージを返します。"
    (.getImage this))

  (images [this]
    "ウィジェットに設定されているイメージの配列を返します。"
    (.getImages this))

  (location [this]
    "ウィジェットの位置を返します。"
    (Point->point (.getLocation this)))

  (maximumed? [this]
    "最大化していたら真を返します。"
    (.getMaximumed this))

  (menu-bar [this]
    "ウィジェットに設定されているメニューバーを返します。"
    (.getMenuBar this))

  (minimized? [this]
    "最小化していたら真を返します。"
    (.getMinimize this))

  (text [this]
    "ウィジェットに設定されているテキストを返します。"
    (.getText this))

  (reparentable? [this] ""
    (.getReparentable this))

  (default-button! [this button]
    "ウィジェットに既定のボタンを設定します。"
    (.setDefaultButton this button)))
