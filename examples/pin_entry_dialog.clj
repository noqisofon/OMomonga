(ns ghostly.momonga.pin_entry_dialog
  (:require [ghostly.momonga.guikit :refer :all]
            [ghostly.momonga.guikit.layout :refer :all]
            [ghostly.momonga.guikit.widgets :refer :all]
            [ghostly.momonga.guikit.widgets.button :refer :all]
            [ghostly.momonga.guikit.widgets.label :refer :all]
            [ghostly.momonga.guikit.widgets.text :refer :all]
            [ghostly.momonga.guikit.widgets.window :refer :all]
            [ghostly.momonga.twitter :refer :all]))


(within-main-loop [display]
                  (let [main-window (window display
                                            :parent nil
                                            :style [ :dialog-trim :application-modal ]
                                            :title "PIN 入力"
                                            :size-request (struct size 100 200)
                                            :layout (struct-map grid-layout
                                                      :num-columns 4
                                                      :columns-equals-width false))
                        label (label main-window
                                     :text "PIN: "
                                     :layout-data (struct-map grid-data
                                                    :horizontal-alignment :end
                                                    :vertical-alignment :center))
                        pin-text (text-box main-window
                                           :style [ :single :border ]
                                           :layout-data (struct-map grid-data
                                                          :horizontal-alignment :fill
                                                          :horizontal-span 3
                                                          :grab-excess-horizontal-space true
                                                          :vertical-alignment :center))
                        ok-button (button main-window
                                          :text "認証"
                                          :layout-data (struct-map grid-data
                                                         :horizontal-span 4
                                                         :horizontal-alignment :end
                                                         :vertical-alignment :end
                                                         :width-hint 40))
                        twitter (make-twitter-client)
                        a_request-token (request-token twitter)
                        web-browser (find-program "html")]
                    (add-hook ok-button :clicked (fn [selected-event]
                                                   (let [pin (text pin_text)]
                                                     (try
                                                       (def access-token 
                                                         (if (pos? (length pin))
                                                           (access-token twitter a_request-token pin)
                                                           ;; else
                                                           (access-token twitter a_request-token)))
                                                       (catch TwitterException te
                                                         (if (= (.getStatusCode te) 401)
                                                           (message-box "Unable to get the access token.")
                                                           ;; else
                                                           (.printStackTrace te)))))
                                                   (close main-window)))
                    (.execute web-browser (authorization-url a_request-token))))
