(ns ghostly.momonga.graphics
  (:import (org.eclipse.swt SWT)
           (org.eclipse.swt.graphics Color
                                     Point
                                     Rectangle)))


(defstruct color :red :blue :green)

(defstruct point :x :y)

(defstruct size :width :height)

(defstruct rectangle :x :y :width :height)


(defn Color->color [^Color a_color]
  (struct color
          (. a_color red)
          (. a_color blue)
          (. a_color green)))


(defn Point->point [^Point a_point]
  (struct point
          (. a_point x)
          (. a_point y)))


(defn point->Point [a_point]
  (Point. (a_point :x)
          (a_point :y)))


(defn size->Point [a_size]
  (Point. (a_size :width)
          (a_size :height)))


(defn Point->size [^Point a_point]
  (struct size
          (. a_point x)
          (. a_point y)))


(defn Rectangle->rectangle [^Rectangle a_rectangle]
  (struct rectangle
          (. a_rectangle x)
          (. a_rectangle y)
          (. a_rectangle width)
          (. a_rectangle height)))
