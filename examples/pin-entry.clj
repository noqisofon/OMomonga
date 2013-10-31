(ns ghostly.momonga.pin_entry
  (:require [ghostly.momonga.guikit :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]))


(within-main-loop [display]
                  (let [shell (window display :title "PIN Entry" :layout (struct-map grid-layout :num-columns 2 :columns-equals-width false))
                        label0 (label shell :text "下記の URL から Twitter にログインし、PIN コードを入力してください。" :grid-data (struct-map grid-data :horizontal-alignment :fill :grab-excess--horizontal true))
                        label1 (label shell :text "PIN: " :grid-data (struct-map grid-data :horizontal-alignment :fill :grab-excess--horizontal true))
                        text-entry (text shell :grid-data (struct-map grid-data :horizontal-alignment :fill :grab-excess--horizontal true))
                        btn (button shell :label "OK" :grid-data (struct-map grid-data :horizontal-alignment :fill :grab-excess--horizontal true))]
                    ))
