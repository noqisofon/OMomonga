(ns ghostly.momonga.graphics
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.graphics Color
                                     Point
                                     Rectangle)))


(defstruct color :red :blue :green)

(defstruct point :x :y)

(defstruct size :width :height)

(defstruct rectangle :x :y :width :height)


(defn to-point [^Point a_point]
  (struct point
          (. a_point x)
          (. a_point y)))


(defn point->size [^Point a_point]
  (struct size
          (. a_point x)
          (. a_point y)))


(defn to-rectangle [^Rectangle a_rectangle]
  (struct rectangle
          (. a_rectangle x)
          (. a_rectangle y)
          (. a_rectangle width)
          (. a_rectangle height)))
