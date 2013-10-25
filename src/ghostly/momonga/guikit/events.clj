(ns ghostly.momonga.guikit.events
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events DisposeListener
                                   SelectionListener)
           (org.eclipse.swt.layout FillLayout
                                   GridLayout
                                   GridData)
           (org.eclipse.swt.graphics Point)
           (org.eclipse.swt.widgets Display
                                    Shell
                                    Listener
                                    Event
                                    Widget
                                    Control))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defstruct event :data :display :time :widget)


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
   :selection SWT/Selection

   :focus-in SWT/FocusIn
   :focus-out SWT/FocusOut

   :expand SWT/Expand
   :collapse SWT/Collapse
   :iconify SWT/Iconify

   :close SWT/Close
   :dispose SWT/Dispose
   :modify SWT/Modify
   :verify SWT/Verify

   :show SWT/Show
   :hide SWT/Hide

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
  ;; widget-or-control は Widget または Control 
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  (assert (symbol? an_event-type))
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
    (.isListening widget-or-control swt-event-type)))


(defn widget-listeners [widget-or-control an_event-type]
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
    (.getListeners widget-or-control swt-event-type)))


(defn widget-notify [widget-or-control an_event-type an_event]
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  (.notifyListener widget-or-control (event-type-symbol-alist an_event-type) an_event))


(defn widget-dispose-hook [widget-or-control hook-fn]
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  (let [a_dipose-listener (reify DisposeListener
                            (widgetDisposed [this a_dispose-event]
                              (hook-fn this a_dispose-event)))]
        (.addDisposeListener widget-or-control a_dipose-listener)
        a_dipose-listener)))


(defn widget-listener-hook [widget-or-control an_event-type hook-fn]
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  (let [an_listener (reify Listener
                      (handleEvent [this an_event]
                        (hook-fn this an_event)))
        swt-event-type (event-type-symbol-alist an_event-type)]
    (.addListener widget-or-control swt-event-type an_listener)
    an_listener))
