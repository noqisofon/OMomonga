(ns ghostly.momonga.guikit
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
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defmacro within-main-loop "bindings => [display-name]" [bindings & body]
  (cond
   (= (count bindings) 0) `(do
                             ~@body)
   (symbol? (bindings 0)) `(let [~(bindings 0) (Display.)]
                             (do
                               ~@body)
                             (let [~'root-window (first (.getShells ~(bindings 0)))]
                               (.pack ~'root-window)
                               (.open ~'root-window)
                               (until (.isDisposed ~'root-window)
                                      (do
                                        (if-not (.readAndDispatch ~(bindings 0))
                                          (.sleep ~(bindings 0)))))
                               (.dispose ~(bindings 0))))))
