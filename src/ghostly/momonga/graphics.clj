(ns ghostly.momonga.graphics
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.graphics Point)))


(defstruct color :red :blue :green)

(defstruct point :x :y)

(defstruct size :width :height)

(defstruct rectangle :x :y :width :height)


(defn point->size
  ([^Point a_point]
     (struct size
             (. a_point x)
             (. a_point y))))
