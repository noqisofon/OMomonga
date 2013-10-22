(ns ghostly.momonga.guikit.widgets
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


(def event-type-symbol-alist
  {
   :none SWT/None

   :key-down SWT/KeyDown
   :key-up SWT/KeyUp

   :mouse-down SWT/MouseDown
   :mouse-up SWT/MouseUp
   :mouse-enter SWT/MouseEnter
   :mouse-hover SWT/MouseHover
   :mouse-exit SWT/MouseExit
   :mouse-double-click SWT/MouseDoubleClick

   :paint SWT/Paint
   :move SWT/Move
   :resize SWT/Resize
   :dispose SWT/Dispose
   :selection SWT/Selection

   :focus-in SWT/FocusIn
   :focus-out SWT/FocusOut

   :expand SWT/Expand
   :collapse SWT/Collapse
   :iconify SWT/Iconify

   :close SWT/Show
   :hide SWT/Hide

   :modify SWT/Modify
   :verify SWT/Verify

   :activate SWT/Activate
   :deactivate SWT/Deactivate

   :drag-detect SWT/DragDetect
   :menu-detect SWT/MenuDetect

   :arm SWT/Arm
   :traverse SWT/Traverse

   :hard-key-down SWT/HardKeyDown
   :hard-key-up SWT/HardKeyUp

   :help SWT/Help
   })


(defn listening? [widget-or-control an_event-type]
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
    (.isListening widget-or-control swt-event-type)))


(defn widget-data 
  ([widget-or-control]
     (.getData widget-or-control))
  ([widget-or-control a_key]
     (.getData widget-or-control a_key)))


(defn widget-style [widget-or-control]
  (.getStyle widget-or-control))


(defn widget-display [widget-or-control]
  (.getDisplay widget-or-control))


(defn widget-listeners [widget-or-control an_event-type]
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
    (.getListeners widget-or-control swt-event-type)))


(defn widget-dispose [widget-or-control]
  (.dispose widget-or-control))


(defn widget-reskin [widget-or-control a_flags]
  (.reskin widget-or-control a_flags))


(defn set-widget-data! [widget-or-control a_data & [a_key]]
  (if a_key
    (.setData widget-or-control a_data)
    ;; else
    (.setData widget-or-control (str a_key) a_data)))


(defn make-label [widget-or-window & {a_text :text a_style :style}]
  (let [a_label (Label. widget-or-window (if-absent a_style SWT/NULL))]
    (if a_text
      (.setText a_label a_text))
    a_label))
