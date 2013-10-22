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
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(def ^:dynamic *display* (Display.))


(defn selected-window
  ([]
     (selected-window *display*))
  ([a_display]
     (.getActiveShell a_display)))


(defn root-window
  ([]
     (root-window *display*))
  ([a_display]
     (first (.getShells a_display))))


(defn window-children
  ([]
     (let [active-window (selected-window)]
       (window-children active-window)))
  ([window-only]
     (.getShells window-only)))


(defn- window-conpute-size-aux
  ([window-only a_size]
     (.computeSize window-only
                   (a_size :width)
                   (a_size :height)))
  ([window-only a_width a_height]
     (.computeSize window-only a_width a_height)))


(defn window-location
  ([]
     (let [active-window (selected-window)]
       (window-location active-window)))
  ([window-only]
     (to-point (.getLocation window-only))))


(defn window-bounds
  ([window-only]
     (to-rectangle (.getBounds window-only))))


(defn set-window-bounds!
  ([window-only a_rectangle]
     (set-window-bounds! window-only
                         (a_rectangle :x)
                         (a_rectangle :y)
                         (a_rectangle :width)
                         (a_rectangle :height)))
  ([window-only x y a_width a_height]
     (.setBounds window-only x y a_width a_height)))


(defn set-window-bounds-size!
  ([window-only a_size]
     (set-window-bounds! window-only
                         SWT/DEFAULT
                         SWT/DEFAULT
                         (a_size :width)
                         (a_size :height)))
  ([window-only a_width a_height]
     (set-window-bounds! window-only
                         SWT/DEFAULT
                         SWT/DEFAULT
                         a_width
                         a_height)))


(defn set-window-size!
  ([window-only a_size]
     (let [a_swt_size (window-conpute-size-aux window-only a_size)]
       (.setSize window-only a_swt_size)))
  ([window-only a_width a_height]
     (let [a_swt_size (window-conpute-size-aux window-only a_width a_height)]
       (.setSize window-only a_swt_size))))


(defn set-window-minimum-size!
  ([window-only a_size]
     (let [a_swt_size (window-conpute-size-aux window-only a_size)]
       (.setMinimumSize window-only a_swt_size)))
  ([window-only a_width a_height]
     (let [a_swt_size (window-conpute-size-aux window-only a_width a_height)]
       (.setMinimumSize window-only a_swt_size))))


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
      (let [a_size (window-size window-only)]
        (a_size :height))))


(defn window-width
  ([]
     (let [active-window (selected-window)]
       (window-width active-window)))
  ([window-only]
     (let [a_size (window-size window-only)]
       (a_size :width))))


(defn window-minimum-size
  ([]
     (let [active-window (selected-window)]
       (window-minimum-size active-window)))
  ([window-only]
     (point->size (.getMinimumSize window-only))))


(defn window-minimum-height
  ([]
     (let [active-window (selected-window)]
       (window-height active-window)))
  ([window-only]
      (let [a_size (window-minimum-size window-only)]
        (a_size :height))))


(defn window-minimum-width
  ([]
     (let [active-window (selected-window)]
       (window-width active-window)))
  ([window-only]
     (let [a_size (window-minimum-size window-only)]
       (a_size :width))))


(defn- window-aux [a_display & {a_title :title a_style :style a_layout :layout }]
  (let [result-window (if a_style
                        (Shell. a_display a_style)
                        ;; else
                        (Shell. a_display))]
    (if a_title
      (.setText result-window a_title))
    (.setLayout result-window (if-absent a_layout (FillLayout.)))
    result-window))


(defn window [& {a_title :title a_style :style a_layout :layout}]
  (window-aux *display* :title a_title :style a_style :layout a_layout))


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


(defn window-pack-and-open [a_root-window & [a_width a_height] ]
  (if (every? pos? [a_width a_height])
    (doto a_root-window
      (.setSize a_width a_height)
      (.open))
    ;; else
    (doto a_root-window
      (.pack)
      (.open))))


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
