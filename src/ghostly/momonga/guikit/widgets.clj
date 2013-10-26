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
            [ghostly.momonga.utils.macros :refer :all]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(def menu-style-alist
  {
   :bar SWT/BAR
   :drop-down SWT/DROP_DOWN
   :pop-up SWT/POP_UP

   :no-radio-group SWT/NO_RADIO_GROUP

   :left-to-right SWT/LEFT_TO_RIGHT
   :right-to-left SWT/RIGHT_TO_LEFT
   })


(def control-style-alist
  {
   :border SWT/BORDER
   :left-to-right SWT/LEFT_TO_RIGHT
   :right-to-left SWT/RIGHT_TO_LEFT
   })


(def scrollable-style-alist
  (merge {
          :h-scroll SWT/H_SCROLL
          :v-scroll SWT/V_SCROLL
          }
         control-style-alist))


(def composite-style-alist
  (merge {
          :no-background SWT/NO_BACKGROUND
          :no-focus SWT/NO_FOCUS
          :no-redraw-resize SWT/NO_REDRAW_RESIZE
          :no-merge-paints SWT/NO_MERGE_PAINTS
          :no-radio-group SWT/NO_RADIO_GROUP

          :embedded SWT/EMBEDDED
          :double-buffered SWT/DOUBLE_BUFFERED
          :up SWT/UP
          }
         scrollable-style-alist))


(def dialog-style-alist
  {
   :primary-modal SWT/PRIMARY_MODAL
   :application-modal SWT/APPLICATION_MODAL
   :system-modal SWT/SYSTEM_MODAL
   })


(def file-dialog-alist
  (merge {
          :multi SWT/MULTI
          :open SWT/OPEN
          :save SWT/SAVE
          }
         dialog-style-alist))


(def button-style-alist
  (merge {
          :toggle SWT/TOGGLE
          :arrow SWT/ARROW
          :push SWT/PUSH
          :radio SWT/RADIO
          :check SWT/CHECK
          :flat SWT/FLAT

          :up SWT/UP
          :down SWT/DOWN
          :left SWT/LEFT
          :right SWT/RIGHT
          :center SWT/CENTER
          }
         control-style-alist))


(def label-style-alist
  (merge {
          :separator SWT/SEPARATOR
          :wrap SWT/WRAP
          :shadow-in SWT/SHADOW_IN
          :shadow-out SWT/SHADOW_OUT
          }
         control-style-alist))


(def progress-bar-style-alist
  (merge {
          :indeterminate SWT/INDETERMINATE
          :min SWT/MIN
          :smooth SWT/SMOOTH

          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def scale-style-alist
  (merge {
          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def slider-style-alist
  (merge {
          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def combo-style-alist
  (merge {
          :drop-down SWT/DROP_DOWN
          :read-only SWT/READ_ONLY
          :simple SWT/SIMPLE
          }
         composite-style-alist))


(defn- to-swt-style-value [a_style-alist a_styles]
  (if (seq? a_styles)
    (do
      ;; a_styles の要素は全てシンボルであること。
      (assert (every? symbol? a_styles))
      ;; a_style-alist のキーではないキーワードが入っている場合は nil が含まれているので、
      ;; フィルタリングします。
      (reduce bit-or (filter #(not (nil? %)) (map a_style-alist a_styles))))
    ;; else
    (if (nil? a_styles)
      SWT/NULL
      ;; else
      (a_style-alist a_styles))))


(defn- to-style-value [a_style-alist swt-style-value]
  (let [reverse-alist (apply assoc {} (interleave (vals a_style-alist) (keys a_style-alist)))
        has-vlist (filter #(pos? %) (map #(bit-and (a_style-alist %) swt-style-value) (keys a_style-alist)))]
    (map #(get reverse-alist %) has-vlist)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Layout 関連
;;
(defstruct fill-layout :margin-height :margin-width :spacing :type)


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-fill-layout
  "org.eclipse.swt.layout.FillLayout を struct fill-layout に変換して返します。"
  [^FillLayout a_layout]
  (struct fill-layout
          (. a_layout marginHeight)
          (. a_layout marginWidth)
          (. a_layout spacing)
          (. a_layout type)))


(defstruct grid-layout
  :horizontal-alignment :vertical-alignment
  :width-hint :height-hint
  :horizontal-indent :verical-indent
  :horizontal-span :verical-span
  :grab-excess-horizontal-space :grab-excess-vertical-space
  :minimum-width :minimum-height
  :exclude)


(defn ^{:requires-bindings true
        :category "instance creation"
        :subcategory nil
        :added "0.1" }
  make-grid-layout
  "キーワードと引数から struct grid-layout を作成して返します。"
  [ & {horizontal-alignment :horizontal-alignment
       vertical-alignment :vertical-alignment
       width-hint :width-hint
       height-hint :height-hint
       horizontal-indent :horizontal-indent
       verical-indent :verical-indent
       horizontal-span :horizontal-span
       verical-span :verical-span
       grab-excess-horizontal-space :grab-excess-horizontal-space
       grab-excess-vertical-space :grab-excess-vertical-space
       minimum-width :minimum-width
       minimum-height :minimum-height
       exclude :exclude}]
  (struct grid-layout
          horizontal-alignment vertical-alignment
          width-hint height-hint
          horizontal-indent verical-indent
          horizontal-span verical-span
          grab-excess-horizontal-space grab-excess-vertical-space
          minimum-width minimum-height
          exclude))


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
  "コントロールのフォントを帰します。"
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
  "古ントロールの親を返します。"
  [a_control]
  (.getParent a_control))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Label 関連
;;
(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  label?
  "引数 label-or-control がラベルなら真を返します。"
  [label-or-control]
  (instance? Label label-or-control))


(defn  ^{:requires-bindings true
         :category "instance creation"
         :subcategory nil
         :added "0.1" }
  label
  "親窓を受け取り、ラベルオブジェクトを作成して返します。"
  [widget-or-window & {a_text :text a_styles :style}]
  (let [swt-style-value (to-swt-style-value label-style-alist a_styles)
        a_label (Label. widget-or-window swt-style-value)]
    (if a_text
      (.setText a_label a_text))
    a_label))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Text 関連
;;
(defn ^{:requires-bindings true
        :category "testing functionality"
        :subcategory "instance typing"
        :added "0.1" }
  text?
  "引数 label-or-control がテキストなら真を返します。"
  [label-or-control]
  (instance? Label label-or-control))


(defn ^{:requires-bindings true
        :category "instance creation"
        :subcategory nil
        :added "0.1" }
  text
  "親窓を受け取り、テキストオブジェクトを作成して返します。"
  [widget-or-window & {a_text :text a_styles :style}]
  (let [swt-style-value (to-swt-style-value label-style-alist a_styles)
        a_label (Text. widget-or-window swt-style-value)]
    (if a_text
      (.setText a_label a_text))
    a_label))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Button 関連
;;
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
  [parent-window & {a_label :label}]
  (let [a_button (Button. parent-window SWT/NULL)]
    (if a_label
      (.setText a_button a_label))
    a_button))
