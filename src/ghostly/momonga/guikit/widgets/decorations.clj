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
  (bounds [this] "ウィジェットの矩形を返します。")
  (client-area [this] "ウィジェットのクライアントエリアの矩形を返します。")
  (default-button [this] "既定のボタンを返します。")
  (image [this] "ウィジェットに設定されているイメージを返します。")
  (images [this] "ウィジェットに設定されているイメージの配列を返します。")
  (location [this] "ウィジェットの位置を返します。")
  (maximumed? [this] "最大化していたら真を返します。")
  (menu-bar [this] "ウィジェットに設定されているメニューバーを返します。")
  (minimized? [this] "最小化していたら真を返します。")
  (size [this] "ウィジェットの大きさを返します(Point ではなく Size で返します)。")
  (text [this] "ウィジェットに設定されているテキストを返します。"))
