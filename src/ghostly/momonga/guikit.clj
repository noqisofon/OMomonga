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
  (:use [ghostly.momonga.graphics]))


(def *display* (Display.))


(defn widget? [window-or-widget]
  (instance? Shell window-or-widget))


(defn control? [widget-or-control]
  (instance? Control widget-or-control))


(defn control-valid? [window-or-control]
  (.isEnabled window-or-control))


(defn window? [window-only]
  (instance? Shell window-only))


(defn window-live? [window-only]
  (if (widget? window-only)
    (.isDisposed window-only)
    false))


(defn selected-window
  ([]
     (.getActiveShell *display*))
  ([window-only]
     (.setActive window-only)
     window-only))


(defn window-parent
  ([]
     (let [active-window (selected-window)]
       (window-parent active-window)))
  ([window-only]
     (.getShells window-only)))


(defn window-minimum-size
  ([]
     (let [active-window (selected-window)]
       (window-minimum-size active-window)))
  ([window-only]
     (point->size (.getMinimumSize window-only))))


(defn window-total-size
  ([]
     (let [active-window (selected-window)]
       (window-total-size active-window)))
  ([window-only]
     (point->size (.getSize window-only))))


(defn window-total-height
  ([]
     (let [active-window (selected-window)]
       (window-total-height active-window)))
  ([window-only]
      (let [a_size (window-total-size)]
        (a_size :height))))


(defn window-total-width
  ([]
     (let [active-window (selected-window)]
       (window-total-width active-window)))
  ([window-only]
     (let [a_size (window-total-size)]
       (a_size :width))))


(defn root-window
  ([]
     (root-window *display*))
  ([a_display]
     (Shell. a_display)))


(defn window [& [a_window-style]]
  (if a_window-style
    (Shell. *display* a_window-style)
    ;; else
    (Shell. *display*)))


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
