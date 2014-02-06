(ns ^{:doc ""
      :author "ned rihine" }
  ghostly.momonga.guikit.display
  (:gen-class)
  (:import (org.eclipse.swt SWT))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.protocols :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defprotocol DisplayFacade
  ""
  (active-shell [this] "")
  (timer-exec [this milliseconds runnable] ""))

(defn find-display [thread]
  (org.eclipse.swt.widgets.Display/findDisplay thread))

;; (defn app-name []
;;   (org.eclipse.swt.widgets.Display/getAppName))

;; (defn app-version []
;;   (org.eclipse.swt.widgets.Display/getAppVersion))

(defn current-display []
  (org.eclipse.swt.widgets.Display/getCurrent))

(defn default-display []
  (org.eclipse.swt.widgets.Display/getDefault))

(extend-type org.eclipse.swt.widgets.Display

  DisplayFacade
  (active-shell [this]
    ""
    (.getActiveShell this))
  
  (timer-exec [this milliseconds hook-fn]
    ""
    (.timerExec this milliseconds (reify Runnable
                                    (run [self]
                                      (hook-fn this))))))
