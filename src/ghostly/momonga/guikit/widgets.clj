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


(defn make-label [widget-or-window & {a_text :text a_style :style}]
  (let [a_label (Label. widget-or-window (if-absent a_style SWT/NULL))]
    (if a_text
      (.setText a_label a_text))
    a_label))
