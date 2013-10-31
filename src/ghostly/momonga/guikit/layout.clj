(ns ^{:doc "レイアウト用の API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.layout
  (:gen-class)
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; FillLayout 関連
;;
(defstruct fill-layout :margin-height :margin-width :spacing :type)


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  fill-layout?
  "引数 that が struct fill-layout であるかどうか判別します。"
  [that]
  (if (map? that)
    (every? #{:margin-height :margin-width :spacing :type} (keys that))
    ;; else
    false))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-fill-layout
  "org.eclipse.swt.layout.FillLayout を struct fill-layout に変換して返します。"
  [^FillLayout a_layout]
  (struct-map fill-layout
          :margin-height (. a_layout marginHeight)
          :margin-width (. a_layout marginWidth)
          :spacing (. a_layout spacing)
          :type (. a_layout type)))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  asFillLayout
  "struct fill-layout から org.eclipse.swt.layout.FillLayout に変換して返します。"
  [a_fill-layout]
  (let [a_fillLayout (FillLayout.)]
    (set! (. a_fillLayout marginHeight) (if-absent (a_fill-layout :margin-height) 0))
    (set! (. a_fillLayout marginWidth)  (if-absent (a_fill-layout :margin-width) 0))
    (set! (. a_fillLayout spacing)      (if-absent (a_fill-layout :spacing) 0))
    (set! (. a_fillLayout type)         (if-absent (a_fill-layout :type) 0))
    a_fillLayout))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; GridLayout 関連
;;
(defstruct grid-layout :horizontal-spacing :make-columns-equal-width
  :margin-bottom :margin-height :margin-left :margin-right :margin-top :margin-width
  :num-columns :vertical-spacing)


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  grid-layout?
  "引数 that が struct grid-layout であるかどうか判別します。"
  [that]
  (if (map? that)
    (every? #{:horizontal-spacing :make-columns-equal-width
              :margin-bottom :margin-height :margin-left :margin-right :margin-top :margin-width
              :num-columns :vertical-spacing} (keys that))
    ;; else
    false))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-grid-layout
  "org.eclipse.swt.layout.GridLayout を struct grid-layout に変換して返します。"
  [^GridLayout a_layout]
  (struct-map grid-layout
    :horizontal-spacing (.horizontalSpacing a_layout) :make-columns-equal-width (.makeColumnsEqualWidth a_layout)
    :margin-bottom      (.marginBottom a_layout)      :margin-height            (.marginHeight a_layout)
    :margin-left        (.marginLeft a_layout)        :margin-right             (.marginRight a_layout)
    :margin-top         (.marginTop a_layout)         :margin-width             (.marginWidth a_layout)
    :num-columns        (.numColumns a_layout)        :vertical-spacing         (.verticalSpacing a_layout)))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  asGridLayout
  "struct grid-layout から org.eclipse.swt.layout.GridLayout に変換して返します。"
  [a_grid-layout]
  (let [a_gridLayout (GridLayout.)]
    (set! (. a_gridLayout horizontalSpacing)     (if-absent (a_grid-layout :horizontal-spacing) 0))
    (set! (. a_gridLayout makeColumnsEqualWidth) (if-absent (a_grid-layout :make-columns-equal-width) false))
    (set! (. a_gridLayout marginBottom)          (if-absent (a_grid-layout :margin-bottom) 0))
    (set! (. a_gridLayout marginHeight)          (if-absent (a_grid-layout :margin-height) 0))
    (set! (. a_gridLayout marginLeft)            (if-absent (a_grid-layout :margin-left) 0))
    (set! (. a_gridLayout marginRight)           (if-absent (a_grid-layout :margin-right) 0))
    (set! (. a_gridLayout marginTop)             (if-absent (a_grid-layout :margin-top) 0))
    (set! (. a_gridLayout marginWidth)           (if-absent (a_grid-layout :margin-width) 0))
    (set! (. a_gridLayout numColumns)            (if-absent (a_grid-layout :num-columns) 0))
    (set! (. a_gridLayout verticalSpacing)       (if-absent (a_grid-layout :vertical-spacing) 0))
    a_gridLayout))


(defstruct grid-data
  :horizontal-alignment :vertical-alignment
  :width-hint :height-hint
  :horizontal-indent :verical-indent
  :horizontal-span :verical-span
  :grab-excess-horizontal-space :grab-excess-vertical-space
  :minimum-width :minimum-height
  :exclude)


(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  grid-data?
  "引数 that が struct grid-data であるかどうか判別します。"
  [that]
  (if (map? that)
    (every? #{:horizontal-alignment :vertical-alignment
              :width-hint :height-hint
              :horizontal-indent :verical-indent
              :horizontal-span :verical-span
              :grab-excess-horizontal-space :grab-excess-vertical-space
              :minimum-width :minimum-height
              :exclude} (keys that))
    ;; else
    false))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-grid-data
  "org.eclipse.swt.layout.GridLayout を struct grid-layout に変換して返します。"
  [^GridData a_grid-data]
  (struct-map grid-data
    :horizontal-alignment         (.horizontalAlignment a_grid-data)          :vertical-alignment         (.verticalAlignment a_grid-data)
    :width-hint                   (.widthHint a_grid-data)                    :height-hint                (.heightHint a_grid-data)
    :horizontal-indent            (.horizontalIndent a_grid-data)             :verical-indent             (.vericalIndent a_grid-data)
    :horizontal-span              (.horizontalSpan a_grid-data)               :verical-span               (.vericalSpan a_grid-data)
    :grab-excess-horizontal-space (.grabExcessHorizontalSpace a_grid-data)    :grab-excess-vertical-space (.grabExcessVerticalSpace a_grid-data)
    :minimum-width                (.minimumWidth a_grid-data)                 :minimum-height             (.minimumHeight a_grid-data)
    :exclude                      (.exclude a_grid-data)))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  asGridData
  "struct grid-data から org.eclipse.swt.layout.GridData に変換して返します。"
  [a_grid-data]
  (let [a_gridData (GridData.)]
    (set! (. a_gridData horizontalAlignment)       (if-absent (a_grid-data :horizontal-alignment) 0))
    (set! (. a_gridData verticalAlignment)         (if-absent (a_grid-data :vertical-alignment) 0))
    (set! (. a_gridData widthHint)                 (if-absent (a_grid-data :width-hint) 0))
    (set! (. a_gridData heightHint)                (if-absent (a_grid-data :height-hint) 0))
    (set! (. a_gridData horizontalIndent)          (if-absent (a_grid-data :horizontal-indent) 0))
    (set! (. a_gridData vericalIndent)             (if-absent (a_grid-data :verical-indent) 0))
    (set! (. a_gridData horizontalSpan)            (if-absent (a_grid-data :horizontal-span) 0))
    (set! (. a_gridData vericalSpan)               (if-absent (a_grid-data :verical-span) 0))
    (set! (. a_gridData grabExcessHorizontalSpace) (if-absent (a_grid-data :grab-excess-horizontal-space) false))
    (set! (. a_gridData grabExcessVerticalSpace)   (if-absent (a_grid-data :grab-excess-vertical-space) false))
    (set! (. a_gridData minimumWidth)              (if-absent (a_grid-data :minimum-width) 0))
    (set! (. a_gridData minimumHeight)             (if-absent (a_grid-data :minimum-height) 0))
    (set! (. a_gridData exclude)                   (if-absent (a_grid-data :exclude) false))
    a_gridData))
