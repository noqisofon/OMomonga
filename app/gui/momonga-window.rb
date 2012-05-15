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
      @tree_view = Gtk::TreeView.new

      #
      # @vbox0
      #
      @vbox0.pack_start @tree_view

      #
      # self
      #
      set_size_request 320, 500
      set_border_width 5
      add @vbox0
    end
  end


end
