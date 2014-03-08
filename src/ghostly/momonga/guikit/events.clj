(ns ghostly.momonga.guikit.events
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.events DisposeListener
                                   SelectionListener
                                   SelectionEvent
                                   TypedEvent)
           (org.eclipse.swt.widgets Listener
                                    Event))
  (:require [ghostly.momonga.graphics :refer :all]
            [ghostly.momonga.utils.macros :refer :all]))


(defstruct event :button :character :count :data :detail :display :doit :end :gc :height :index :item :key-code
           :key-location :magunification :rotation :start :state-mask :text :time :touches :type :widget :width
           :x :x-direction :y :y-direction)


(defn Event->event [^Event an_event]
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
          (. an_event yDirection)))

;; (defn event->Event [evt]
;;   )


(defstruct type-event :data :display :time :widget)


(defn TypedEvent->type-event [^TypedEvent an_event]
  (struct type-event
          (. an_event data)
          (. an_event display)
          (. an_event time)
          (. an_event widget)))


(defstruct selection-event :detail :doit :height :item :state-mask :text :width :x :y)


(defn SelectionEvent->selection-event [^SelectionEvent an_event]
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

(defprotocol ListenTarget
  ""
  (add-dispose-listener [this hook-fn] "リッスンターゲットにディスポーズリスナーを追加します。")
  (add-listener [this event-type hook-fn] "リッスンターゲットにリスナーを追加します。")
  (listeners [this event-type] "ウィジェットに関連付けられているリスナーの配列を返します。")
  (listening? [this event-type] "指定されたイベントタイプをウィジェットがリスニングしていたら真を返します。")
  (notify-listeners [this event-type an_event] "イベントタイプとイベントを指定してウィジェットに関連付けられているリスナーを実行させます。")
  (remove-dispose-listener [this listener] "ウィジェットに追加されたディスポーズリスナーを削除します。")
  (remove-listener [this event-type listener] "ウィジェットに追加されたリスナーを削除します。"))


(extend-type org.eclipse.swt.widgets.Widget
  ListenTarget
  (add-dispose-listener [this hook-fn]
    (assert (fn? hook-fn))
    (let [a_dipose-listener (reify org.eclipse.swt.events.DisposeListener
                              (widgetDisposed [self a_dispose-event]
                                (hook-fn self (TypedEvent->type-event a_dispose-event))))]
      (.addDisposeListener this a_dipose-listener)
      a_dipose-listener))

  (add-listener [this event-type hook-fn]
    (assert (keyword? event-type))
    (assert (fn? hook-fn))
    (let [an_listener (reify org.eclipse.swt.widgets.Listener
                        (handleEvent [this evt]
                          (hook-fn this (Event->event evt))))
          swt-event-type (event-type-symbol-alist event-type)]
      (.addListener this swt-event-type an_listener)
      an_listener))

  (listeners [this event-type]
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.getListeners this swt-event-type)))

  (listening? [this event-type]
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.isListening this swt-event-type)))

  (notify-listeners [this event-type evt]
    (assert (keyword? event-type))
    (assert (every? nil? (map evt evt)))
    (.notifyListener this (event-type-symbol-alist event-type) evt))

  (remove-dispose-listener [this listener]
    (.removeDisposeListener this listener))

  (remove-listener [this event-type listener]
    (assert (keyword? event-type))
    (let [swt-event-type (event-type-symbol-alist event-type)]
      (.removeListener this swt-event-type listener))))
