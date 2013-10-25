(ns ghostly.momonga.guikit.events
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events DisposeListener
                                   SelectionListener
                                   SelectionEvent
                                   TypedEvent)
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


(defstruct event :button :character :count :data :detail :display :doit :end :gc :height :index :item :key-code
           :key-location :magunification :rotation :start :state-mask :text :time :touches :type :widget :width
           :x :x-direction :y :y-direction)


(defn as-event [^Event an_event]
  (struct event
          (. an_event button)
          (. an_event character)
          (. an_event count)
          (. an_event data)
          (. an_event detail)
          (. an_event display)
          (. an_event doit)
          (. an_event end)
          (. an_event gc)
          (. an_event height)
          (. an_event index)
          (. an_event item)
          (. an_event keyCode)
          (. an_event keyLocation)
          (. an_event magunification)
          (. an_event rotation)
          (. an_event start)
          (. an_event stateMask)
          (. an_event text)
          (. an_event time)
          (. an_event touches)
          (. an_event type)
          (. an_event widget)
          (. an_event width)
          (. an_event x)
          (. an_event xDirection)
          (. an_event y)
          (. an_event yDirection))


(defstruct type-event :data :display :time :widget)


(defn as-type-event [^TypedEvent an_event]
  (struct type-event
          (. an_event data)
          (. an_event display)
          (. an_event time)
          (. an_event widget)))


(defstruct selection-event :detail :doit :height :item :state-mask :text :width :x :y)


(defn as-selection-event [^SelectionEvent an_event]
  (struct selection-event
          (. an_event detail)
          (. an_event doit)
          (. an_event height)
          (. an_event item)
          (. an_event stateMask)
          (. an_event text)
          (. an_event width)
          (. an_event x)
          (. an_event y)))


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
  ;; widget-or-control は Widget または Control である。
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  ;; an_event-type はキーワードである。
  (assert (keyword? an_event-type))
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
    (.isListening widget-or-control swt-event-type)))


(defn widget-listeners [widget-or-control an_event-type]
  ;; widget-or-control は Widget または Control である。
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  ;; an_event-type はキーワードである。
  (assert (keyword? an_event-type))
  (let [swt-event-type (event-type-symbol-alist an_event-type)]
    (.getListeners widget-or-control swt-event-type)))


(defn widget-notify [widget-or-control an_event-type an_event]
  ;; widget-or-control は Widget または Control である。
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  ;; an_event-type はキーワードである。
  (assert (keyword? an_event-type))
  (.notifyListener widget-or-control (event-type-symbol-alist an_event-type) an_event))


(defn widget-dispose-hook [widget-or-control hook-fn]
  ;; widget-or-control は Widget または Control である。
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  ;; hook-fn は何らかの関数である。
  (assert (fn? hook-fn))
  (let [a_dipose-listener (reify DisposeListener
                            (widgetDisposed [this a_dispose-event]
                              (hook-fn this (as-type-event a_dispose-event))))]
        (.addDisposeListener widget-or-control a_dipose-listener)
        a_dipose-listener)))


(defn widget-listener-hook [widget-or-control an_event-type hook-fn]
  ;; widget-or-control は Widget または Control である。
  (assert (or (widget? widget-or-control)
              (control? widget-or-control)))
  ;; an_event-type はキーワードである。
  (assert (keyword? an_event-type))
  ;; hook-fn は何らかの関数である。
  (assert (fn? hook-fn))
  (let [an_listener (reify Listener
                      (handleEvent [this an_event]
                        (hook-fn this (as-event an_event))))
        swt-event-type (event-type-symbol-alist an_event-type)]
    (.addListener widget-or-control swt-event-type an_listener)
    an_listener))


(defn add-hook [widget-or-control an_event-type hook-fn]
  (.addSelectionListener widget-or-control (proxy [SelectionAdapter] []
                                             (widgetSelected [an_event]
                                               (let [a_selection-event (to-selection-event an_event)]
                                                 (hook-fn a_selection-event))))))
