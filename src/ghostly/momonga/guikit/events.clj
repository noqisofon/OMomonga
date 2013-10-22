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


(defn widget-notify [widget-or-control an_event-type an_event]
  (.notifyListener widget-or-control (event-type-symbol-alist an_event-type) an_event))


(defn widget-dispose-hook [widget-or-control hook-fn]
  (.addDisposeListener widget-or-control (reify DisposeListener
                                           (widgetDisposed [this a_dispose-event]
                                             (hook-fn this a_dispose-event)))))


(defn widget-listener-hook [widget-or-control an_event-type hook-fn]
  (.addListener widget-or-control (event-type-symbol-alist an_event-type) (reify Listener
                                                                            (handleEvent [this an_event]
                                                                              (hook-fn this an_event)))))
