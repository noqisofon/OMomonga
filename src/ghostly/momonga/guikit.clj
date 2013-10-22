(ns ghostly.momonga.guikit
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
  (:require [ghostly.momonga.macros :refer :all]
            [ghostly.momonga.graphics :refer :all]))


(def ^:dynamic *display* (Display.))


(defn widget? [window-or-widget]
  (instance? Widget window-or-widget))


(defn control? [widget-or-control]
  (instance? Control widget-or-control))


(defn window? [widget-or-control]
  (instance? Shell widget-or-control))


(defn enabled? [window-or-control]
  (.isEnabled window-or-control))


(defn dispose? [a_widget]
  (.isDisposed a_widget))


(defn selected-window
  ([]
     (selected-window *display*))
  ([a_display]
     (.getActiveShell a_display)))


(defn root-window
  ([]
     (root-window *display*))
  ([a_display]
     (.getShell a_display)))


(defn window-children
  ([]
     (let [active-window (selected-window)]
       (window-children active-window)))
  ([window-only]
     (.getShells window-only)))


(defn window-minimum-size
  ([]
     (let [active-window (selected-window)]
       (window-minimum-size active-window)))
  ([window-only]
     (point->size (.getMinimumSize window-only))))


(defn window-size
  ([]
     (let [active-window (selected-window)]
       (window-size active-window)))
  ([window-only]
     (point->size (.getSize window-only))))


(defn window-height
  ([]
     (let [active-window (selected-window)]
       (window-height active-window)))
  ([window-only]
      (let [a_size (window-size)]
        (a_size :height))))


(defn window-width
  ([]
     (let [active-window (selected-window)]
       (window-width active-window)))
  ([window-only]
     (let [a_size (window-size)]
       (a_size :width))))


(defn- inner-make-window [a_display & {a_title :title a_style :style a_layout :layout }]
  (let [result-window (if a_style
                        (Shell. a_display a_style)
                        ;; else
                        (Shell. a_display))]
    (if a_title
      (.setText result-window a_title))
    (if a_layout
      (.setLayout result-window a_layout)
      ;; else
      (.setLayout result-window (FillLayout.)))
    result-window))


(defn make-window [& {a_title :title a_style :style a_layout :layout}]
  (inner-make-window *display* :title a_title :style a_style :layout a_layout))


(defn make-label [widget-or-window & {a_text :text a_style :style}]
  (let [a_label (Label. widget-or-window (if a_style a_style SWT/NULL))]
    (if a_text
      (.setText a_label a_text))
    a_label))


(defn window-pack-and-open
  ([a_root-window]
     (doto a_root-window
       (.pack)
       (.open)))
  ([a_root-window a_width a_height]
     (doto a_root-window
       (.pack)
       (.setSize a_width a_height)
       (.open))))


(defn window-style
  ([]
     (let [active-window (selected-window)]
       (window-style active-window)))
  ([window-only]
     (.getStyle window-only)))


(defn window-title
  ([]
     (let [active-window (selected-window)]
       (window-title active-window)))
  ([window-only]
     (.getText window-only)))


(defn main-loop
  ([]
     (let [a_root-window (root-window)]
       (main-loop *display* a_root-window)))
  ([a_root-window]
     (main-loop *display* a_root-window))
  ([a_display a_root-window]
     (until (.isDisposed a_root-window)
            (do
              (if-not (.readAndDispatch a_display)
                (.sleep a_display))))
     (.dispose a_display)))
