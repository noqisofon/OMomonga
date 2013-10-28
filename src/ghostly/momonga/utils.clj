(ns ^{:doc "ありそうで無いユーリティティ API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.utils
  (:import (java.io File))
  (:require [clojure.java.io :as io]))


(defn ^{:requires-bindings true
        :category "environment"
        :subcategory "filesystem"
        :added "0.1" }
  file-separator
  "ファイルパスのセパレータ用の文字列を返します。"
  []
  (System/getProperty "file.separator"))


(defn ^{:requires-bindings true
        :category "environment"
        :subcategory "filesystem"
        :added "0.1" }
  exists?
  "pathname が存在するかどうか判別します。"
  [^String pathname]
  (.exists (file pathname))))


(defn ^{:requires-bindings true
        :category "environment"
        :subcategory "filesystem"
        :added "0.1" }
  mkdir
  "pathname が示すディレクトリを作成します。"
  [^String pathname]
  (.mkdir (file pathname)))


(defn ^{:requires-bindings true
        :category "environment"
        :subcategory "filesystem"
        :added "0.1" }
  mkdir-parents
  "pathname が示すディレクトリを作成します。
親ディレクトリが存在しない場合は作成します。"
  [^String pathname]
  (.mkdirs (file pathname)))
