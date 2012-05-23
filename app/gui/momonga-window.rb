# -*- coding: utf-8 -*-
=begin
  momonga-window.rb にはメイン窓の定義が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require "../ghostly_require.rb"

ghostly_require 'main-window.rb'
ghostly_require 'about-box.rb'


module OMomonga::Gui


  #
  # おばけももんがのメイン窓です。
  #
  class MomongaWindow < MainWindow
    def initialize()
      super "おばけももんが"

      init_widgets
    end

    private
    def init_widgets
      @vbox0 = Gtk::VBox.new( false, 0 )
      @menu_strip = Gtk::MenuBar.new
      @tweets_menu_strip_item = Gtk::MenuItem.new( "ツイート(_T)" )
      @new_tweet_tool_strip_item = Gtk::MenuItem.new( "新しいツイート(_N)" )
      @separator0 = Gtk::SeparatorMenuItem.new
      @quit_tool_strip_item = Gtk::MenuItem.new( "終了(_Q)" )
      @edit_menu_strip_item = Gtk::MenuItem.new( "編集(_E)" )
      @preference_tool_strip_item = Gtk::MenuItem.new( "設定(_P)" )
      @help_menu_strip_item = Gtk::MenuItem.new( "ヘルプ(_H)" )
      @about_tool_strip_item = Gtk::MenuItem.new( "おばけももんがについて(_A)" )
      @tree_view = Gtk::TreeView.new

      #
      # @vbox0
      #
      @vbox0.pack_start @menu_strip, false, false
      @vbox0.pack_start @tree_view

      #
      # @menu_strip
      #
      [ @tweets_menu_strip_item, @edit_menu_strip_item, @help_menu_strip_item ].each do |menu_strip_item|
        @menu_strip.append menu_strip_item
      end

      #
      # @tweets_menu_strip_item
      #
      @tweets_menu_strip_item.submenu = Gtk::Menu.new

      #
      # @new_tweet_tool_strip_item
      #
      @new_tweet_tool_strip_item.signal_connect do "activate" new_tweet_tool_clicked end
      @tweets_menu_strip_item.submenu.append @new_tweet_tool_strip_item

      #
      # @separator0
      #
      @tweets_menu_strip_item.submenu.append @separator0

      #
      # @quit_tool_strip_item
      #
      @quit_tool_strip_item.signal_connect "activate" do quit_tool_clicked end
      @tweets_menu_strip_item.submenu.append @quit_tool_strip_item

      #
      # @edit_menu_strip_item
      #
      @edit_menu_strip_item.submenu = Gtk::Menu.new

      #
      # @preference_tool_strip_item
      #
      @preference_tool_strip_item.signal_connect do "activate" preference_tool_clicked end
      @edit_menu_strip_item.submenu.append @preference_tool_strip_item

      #
      # @help_menu_strip_item
      #
      @help_menu_strip_item.submenu = Gtk::Menu.new

      #
      # @about_tool_strip_item
      #
      @about_tool_strip_item.signal_connect "activate" do about_tool_clicked end
      @help_menu_strip_item.submenu.append @about_tool_strip_item

      #
      # @tree_view
      #

      #
      # self
      #
      set_default_size 320, 500
      set_border_width 5
      add @vbox0
    end

    #
    # 「新しいツイート(_N)」をクリックした時に呼び出されます。
    #
    def new_tweet_tool_clicked
    end

    #
    # 「終了(_Q)」をクリックした時に呼び出されます。
    #
    def quit_tool_clicked
      Gtk.main_quit
    end

    #
    # 「設定(_P)」をクリックした時に呼び出されます。
    #
    def preference_tool_clicked
    end

    #
    # 「おばけももんがについて(_A)」をクリックした時に呼び出されます。
    #
    def about_tool_clicked
      about_box = AboutBox.new
      about_box.show_all
      about_box.run
      about_box.destroy
    end

  end

end


if $0 == __FILE__ then
  window = OMomonga::Gui::MomongaWindow.new
  window.show_all

  Gtk.main
end

