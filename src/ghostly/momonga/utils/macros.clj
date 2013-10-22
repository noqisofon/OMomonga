(ns ghostly.momonga.utils.macros)


(defmacro if-absent [default-value absent-value]
  `(if ~default-value
     ~default-value
     ;; else
     ~absent-value))


(defmacro until [test expr]
  `(while (not ~test)
     ~expr))
