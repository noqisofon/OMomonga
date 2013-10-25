(ns ghostly.momonga.guikit.widgets
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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn widget? [window-or-widget]
  (instance? Widget window-or-widget))


(defn control? [widget-or-control]
  (instance? Control widget-or-control))


(defn window? [widget-or-control]
  (instance? Shell widget-or-control))


(defn enabled? [window-or-control]
  (.isEnabled window-or-control))


(defn dispose? [display-or-widget]
  (.isDisposed display-or-widget))


(defn focus-control? [a_control]
  (.isFocusControl a_control))


(defn reparentable? [a_control]
  (.isReparentable a_control))


(defn visible? [a_control]
  (.isVisible a_control))


(defn widget-data 
  ([widget-or-control]
     (.getData widget-or-control))
  ([widget-or-control a_key]
     (.getData widget-or-control a_key)))


(defn widget-style [widget-or-control]
  (.getStyle widget-or-control))


(defn widget-display [widget-or-control]
  (.getDisplay widget-or-control))


(defn widget-dispose [widget-or-control]
  (.dispose widget-or-control))


(defn widget-reskin [widget-or-control a_flags]
  (.reskin widget-or-control a_flags))


(defn set-widget-data! [widget-or-control a_data & [a_key]]
  (if a_key
    (.setData widget-or-control a_data)
    ;; else
    (.setData widget-or-control (str a_key) a_data)))


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


(defn label [widget-or-window & {a_text :text a_styles :style}]
  (let [swt-style-value (to-swt-style-value label-style-alist a_styles)
        a_label (Label. widget-or-window swt-style-value)]
    (if a_text
      (.setText a_label a_text))
    a_label))
