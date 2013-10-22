(ns ghostly.momonga.sketch_guikit_with_label
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Label)))


(defmacro if-absent [default-value absent-value]
  `(if ~default-value
     ~default-value
     ;; else
     ~absent-value))


(defmacro until [test expr]
  `(while (not ~test)
     ~expr))


(def ^:dynamic *display* (Display.))


(defn root-window
  ([]
     (root-window *display*))
  ([a_display]
     (.getShell a_display)))


(defn- inner-make-window [a_display & {a_title :title a_style :style a_layout :layout }]
  (let [result-window (if a_style
                        (Shell. a_display a_style)
                        ;; else
                        (Shell. a_display))]
    (if a_title
      (.setText result-window a_title))
    (.setLayout result-window (if-absent a_layout (FillLayout.)))
    result-window))


(defn make-window [& {a_title :title a_style :style a_layout :layout}]
  (inner-make-window *display* :title a_title :style a_style :layout a_layout))


(defn make-label [widget-or-window & {a_text :text a_style :style}]
  (let [a_label (Label. widget-or-window (if-absent a_style SWT/NULL))]
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


(defn main []
  (let [a_root-window (make-window :title "Label Demo")
        a_label (make-label a_root-window :text "Hello, World!")]
    (window-pack-and-open a_root-window 300 100)
    (main-loop a_root-window)))


(main)
