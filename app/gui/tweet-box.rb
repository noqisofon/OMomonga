# -*- coding: utf-8 -*-
=begin
  tweet-box.rb にはついーと用ダイアログが入っています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'gtk2'

require File.expand_path( "../ghostly_require.rb", File.dirname( __FILE__ ) )


module OMomonga::Gui


  class TweetBox < Gtk::Window
    def initialize
      super "ついーと窓"

      init_widgets
    end

    private
    def init_widgets
      @vbox0 = Gtk::VBox.new( false, 0 )
      @tweet_textview = Gtk::TextView.new
      @button_box = Gtk::HButtonBox.new
      @clear_button = Gtk::Button.new
      @tweet_button = Gtk::Button.new

      #
      # @vbox0
      #
      @vbox0.pack_start @tweet_textview
      @vbox0.pack_start @button_box, false, false, 4

      #
      # @tweet_textview
      #

      #
      # @button_box
      #
      @button_box.layout_style = Gtk::ButtonBox::END
      @button_box.add @clear_button
      @button_box.add @tweet_button

      #
      # @clear_button
      #
      @clear_button.label = "クリア"
      @clear_button.signal_connect "clicked" do clear_button_clicked end

      #
      # @tweet_button
      #
      @tweet_button.label = "更新"
      @tweet_button.signal_connect "clicked" do tweet_button_clicked end

      #
      # self
      #
      signal_connect "destroy" do destroyed end
      set_default_size 400, 180
      set_border_width 10
      set_resizeable = false
      add @vbox0
    end

    #
    #
    #
    def clear_button_clicked
      @tweet_textview.buffer.text = ""
    end

    #
    #
    #
    def tweet_button_clicked
      p @tweet_textview.buffer.text
    end

    #
    #
    #
    def destroyed
      Gtk.main_quit if transient_for.nil?
    end
  end


end


if $0 == __FILE__ then
  tweet_box = OMomonga::Gui::TweetBox.new
  tweet_box.show_all

  Gtk.main
end
