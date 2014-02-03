(ns ghostly.momonga.utils.protocols)


(defprotocol Disposable
  ""
  (dispose [this] "マネージリソースを破棄します。")
  (dispose? [this] "マネージリソースが破棄されていた際に真を返します。"))
