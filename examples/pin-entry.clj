(ns ghostly.momonga.pin_entry
  (:require [ghostly.momonga.guikit :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]))


(within-main-loop [display]
                  (let [shell (window display :title "PIN Entry" :layout :grid-layout)
                        label0 (label shell :label "下記の URL から Twitter にログインし、PIN コードを入力してください。")
                        label1 (label shell :label "PIN: ")
                        text-entry (text shell)
                        btn (button shell)]
                    ))
