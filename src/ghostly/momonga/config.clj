(ns ^{:doc "設定ファイル関連の API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.config
  (:require [clojure.string :as string]
            [ghostly.momonga.utils :refer :all]))


(def application-registration-name "ghostly-momonga")


(def application-name "お化けももんが")


(defn ^{:requires-bindings true
        :category "configuration"
        :subcategory nil
        :added "0.1" }
  user-home-dir
  "ユーザのホームディレクトリのパスを返します。"
  []
  (System/getProperty "user.home"))


(defn- user-dirpath
  "ユーザー用の設定、キャッシュ用パスを返します。"
  [dirname]
  (string/join (file-separator) [ (user-home-dir) (str "." application-registration-name) dirname ]))


(defn ^{:requires-bindings true
        :category "configuration"
        :subcategory nil
        :added "0.1" }
  user-config-dir
  "ユーザ用の設定ファイル用ディレクトリのパスを返します。

ユーザ用の設定ファイル用ディレクトリが存在しない場合は作成します。"
  []
  (let [config-path (user-dirpath "config")]
    (if-not (exists? config-path)
      (mkdir-parents config-path))
    config-path))


(defn ^{:requires-bindings true
        :category "configuration"
        :subcategory nil
        :added "0.1" }
  user-cache-dir
  "ユーザ用のキャッシュファイル用ディレクトリのパスを返します。

ユーザ用のキャッシュファイル用ディレクトリが存在しない場合は作成します。"
  []
  (let [cache-path (user-dirpath "cache")]
    (if-not (exists? cache-path)
      (mkdir-parents cache-path))
    cache-path))
