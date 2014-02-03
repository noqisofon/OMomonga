(ns ^{:doc ""
      :author "ned rihine" }
  ghostly.momonga.guikit.widgets.display
  (:gen-class)
  (:import (org.eclipse.swt SWT))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.styles :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.utils.protocols :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defprotocol Display
  ""
  (add-filter [this event-type listener] "")
  (add-listener [this event-type listener] "")
  (async-exec [this runnable] "")
  (beep [this] "")
  (dispose-exec [this runnable] "")
  (find-widget [this handle]
    [this handle id]
    [this widget id] "")
  (active-shell [this] "")
  (bounds [this] "")
  (client-area [this] "")
  (cursor-control [this] "")
  (cursor-location [this] "")
  (cursor-sizes [this] "")
  (data [this]
    [this key] "")
  (dismissal-alignment [this] "")
  (double-click-time [this] "")
  (focus-control [this] "")
  (high-contrast? [this] "")
  (icon-depath [this] "")
  (icon-sizes [this] "")
  (menu-bar [this] "")
  (monitors [this] "")
  (primary-monitor [this] ""))

(defn find-display [thread]
  (org.eclipse.swt.widgets.Display/findDisplay thread))

(defn app-name []
  (org.eclipse.swt.widgets.Display/getAppName))

(defn app-version []
  (org.eclipse.swt.widgets.Display/getAppVersion))

(defn current-display []
  (org.eclipse.swt.widgets.Display/getCurrent))

(defn default-display []
  (org.eclipse.swt.widgets.Display/getDefault))
