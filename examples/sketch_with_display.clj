(ns ghostly.momonga.sketch_with_display
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events SelectionAdapter
                                   SelectionListener)
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
                                    Control
                                    MessageBox)))



(defmacro in-display
  "bindings => [name]" [bindings & body]
  ;; bindings (引数ベクトル)の数が:
  (cond
   ;;  0 である場合、body だけになる。
   (= (count bindings) 0) `(do
                             ~@body)
   ;;  1 である場合、bindings の 0 番目に Display オブジェクトが渡される。
   (symbol? (bindings 0)) `(let [~(bindings 0) (Display.)]
                             (try
                               (do
                                 ~@body)
                               (finally
                                 (.dispose ~(bindings 0)))))))


(defmacro with-widget
  "bindings => [name init]" [bindings & body]
  (cond
   (= (count bindings) 0) `(do
                             ~@body)
   (symbol? (bindings 0)) `(let ~(subvec bindings 0 2)
                             (do
                               ~@body))))


(in-display [display]
            (with-widget [shell (doto (Shell. display)
                                  (.setText "Button Demo")
                                  (.setLayout (GridLayout. 1 true)))]
              (with-widget [button (doto (Button. shell SWT/NULL)
                                     (.setText "OK"))]
                (.addSelectionListener button (proxy [SelectionAdapter] []
                                                (widgetSelected [an_event]
                                                  (doto (MessageBox. shell)
                                                    (.setMessage "ボタンがクリックされました。")
                                                    (.open))))))
              (doto shell
                (.setSize 200 100)
                (.open))

              (while (not (.isDisposed shell))
                (do
                  (if-not (.readAndDispatch display)
                    (.sleep display))))
              ))
