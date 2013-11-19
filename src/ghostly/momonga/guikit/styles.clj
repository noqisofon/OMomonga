(ns ^{:doc "スタイルの定数？とそれ用の低レベルな API 群です。"
      :author "Ned Rihine" }
  ghostly.momonga.guikit.styles
  (:import (org.eclipse.swt SWT)))


(def menu-style-alist
  {
   :bar SWT/BAR
   :drop-down SWT/DROP_DOWN
   :pop-up SWT/POP_UP

   :no-radio-group SWT/NO_RADIO_GROUP

   :left-to-right SWT/LEFT_TO_RIGHT
   :right-to-left SWT/RIGHT_TO_LEFT
   })


(def control-style-alist
  {
   :border SWT/BORDER
   :left-to-right SWT/LEFT_TO_RIGHT
   :right-to-left SWT/RIGHT_TO_LEFT
   })


(def scrollable-style-alist
  (merge {
          :h-scroll SWT/H_SCROLL
          :v-scroll SWT/V_SCROLL
          }
         control-style-alist))


(def composite-style-alist
  (merge {
          :no-background SWT/NO_BACKGROUND
          :no-focus SWT/NO_FOCUS
          :no-redraw-resize SWT/NO_REDRAW_RESIZE
          :no-merge-paints SWT/NO_MERGE_PAINTS
          :no-radio-group SWT/NO_RADIO_GROUP

          :embedded SWT/EMBEDDED
          :double-buffered SWT/DOUBLE_BUFFERED
          :up SWT/UP
          }
         scrollable-style-alist))


(def decorations-style-list
  (merge {
          :tool SWT/TOOL
          :no-trim SWT/NO_TRIM
          :resize SWT/RESIZE
          :title SWT/TITLE
          :close SWT/CLOSE
          :min SWT/MIN
          :max SWT/MAX
          :border SWT/BORDER
          :on-top SWT/ON_TOP
          }
         composite-style-alist))


(def dialog-style-alist
  {
   :primary-modal SWT/PRIMARY_MODAL
   :application-modal SWT/APPLICATION_MODAL
   :system-modal SWT/SYSTEM_MODAL
   })


(def file-dialog-alist
  (merge {
          :multi SWT/MULTI
          :open SWT/OPEN
          :save SWT/SAVE
          }
         dialog-style-alist))


(def button-style-alist
  (merge {
          :toggle SWT/TOGGLE
          :arrow SWT/ARROW
          :push SWT/PUSH
          :radio SWT/RADIO
          :check SWT/CHECK
          :flat SWT/FLAT

          :up SWT/UP
          :down SWT/DOWN
          :left SWT/LEFT
          :right SWT/RIGHT
          :center SWT/CENTER
          }
         control-style-alist))


(def label-style-alist
  (merge {
          :separator SWT/SEPARATOR
          :wrap SWT/WRAP
          :shadow-in SWT/SHADOW_IN
          :shadow-out SWT/SHADOW_OUT
          }
         control-style-alist))


(def progress-bar-style-alist
  (merge {
          :indeterminate SWT/INDETERMINATE
          :min SWT/MIN
          :smooth SWT/SMOOTH

          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def scale-style-alist
  (merge {
          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def slider-style-alist
  (merge {
          :horizontal SWT/HORIZONTAL
          :vertical SWT/VERTICAL
          }
         control-style-alist))


(def combo-style-alist
  (merge {
          :drop-down SWT/DROP_DOWN
          :read-only SWT/READ_ONLY
          :simple SWT/SIMPLE
          }
         composite-style-alist))


(def shell-style-alist
  (merge {}
         decorations-style-list))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-swt-style-value
  "キーワードの配列から、対応する SWT の定数のビットフィールド値を返します。"
  [a_style-alist a_styles]
  (if (seq? a_styles)
    (do
      ;; a_styles の要素は全てシンボルであること。
      (assert (every? symbol? a_styles))
      ;; a_style-alist のキーではないキーワードが入っている場合は nil が含まれているので、
      ;; フィルタリングします。
      (reduce bit-or (filter #(not (nil? %)) (map a_style-alist a_styles))))
    ;; else
    (if (nil? a_styles)
      SWT/NULL
      ;; else
      (a_style-alist a_styles))))


(defn ^{:requires-bindings true
        :category "convertion"
        :subcategory nil
        :added "0.1" }
  to-style-value
  " SWT の定数のビットフィールド値から対応するキーワードの配列を返します。"
  [a_style-alist swt-style-value]
  (let [style-keys (keys a_style-alist)
        reverse-alist (apply assoc {} (interleave (vals a_style-alist) style-keys))
        has-vlist (filter #(pos? %) (map #(bit-and (a_style-alist %) swt-style-value) style-keys))]
    (map #(get reverse-alist %) has-vlist)))
