# -*- coding: utf-8 -*-
=begin
  momonga-window.rb にはメイン窓の定義が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

ghostly_require 'main-window.rb'


module OMomonga::Gui


  class MomongaWindow < MainWindow
    def initialize()
      super "おばけももんが"

      init_widgets
    end

    private
    def init_widgets
      @vbox0 = Gtk::VBox.new( false, 0 )
      @menu_bar = Gtk::MenuBar.new
      @tree_view = Gtk::TreeView.new

      #
      # @vbox0
      #
      @vbox0.pack_start @menu_bar, false, false
      @vbox0.pack_start @tree_view

      #
      # @menu_bar
      #
      init_menu

      #
      # @tree_view
      #

      #
      # self
      #
      set_size_request 320, 500
      set_border_width 5
      add @vbox0
    end

    def init_menu
      { "ツイート(T)" => [ "新しいツイート(N)", "----", "終了(Q)" ] ,
        "編集(E)" => [ "設定(P)" ],
        "ヘルプ(H)" => [ "おばけももんがとは(A)" ] }.each do |k, v|
        menu_strip_item = Gtk::MenuItem.new( k )
        tool_strip_subitems = Gtk::Menu.new
        v.each do |label|
          unless label =~ /-+/ then
            tool_strip_subitems.append Gtk::MenuItem.new( label )
          else
            tool_strip_subitems.append Gtk::SeparatorMenuItem.new
          end
        end
        menu_strip_item.submenu = tool_strip_subitems

        @menu_bar.append menu_strip_item
      end
    end
  end


end
