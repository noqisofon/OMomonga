(ns ghostly.momonga.sketch_with_display
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



(defmacro in-display
  "bindings => [name]"
  [bindings & body]
  (cond
   (= (count bindings) 0) `(do
                             ~@body)
   (symbol? (bindings 0)) `(let [~(bindings 0) (Display.)]
                             (try
                               (do
                                 ~@body)
                               (finally
                                 (.dispose ~(bindings 0)))))))


(defmacro with-widget [bindings & body]
  (cond
   (= (count bindings) 0) `(do
                             ~@body)
   (symbol? (bindings 0)) `(let ~(subvec bindings 0 2)
                             (do
                               ~@body))))


(in-display [display]
  (with-widget [shell (Shell. display)]
    (println (class shell))))
