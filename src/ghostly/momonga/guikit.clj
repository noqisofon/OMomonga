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
                                    Control)))


(defstruct size :width :height)


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


(defn window-total-size [window-only]
  (struct size
          (. (.getSize window-only) x)
          (. (.getSize window-only) y))))


(defn window-total-height [window-only]
  (let [a-size (window-total-size)]
    (a-size :height)))


(defn window-total-width [window-only]
  (let [a-size (window-total-size)]
    (a-size :width)))
