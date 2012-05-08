# -*- coding: utf-8 -*-
=begin
  main-window.rb にはメイン窓用のクラスとそれにくっつけるモジュールが含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'gtk2'


module OMomonga::Gui


  module DestroyWindowSpec
    def destroy
      super
      @destroyed = true
    end

    def destroyed?
      @destroyed
    end
  end


  module MainWindowSingleton
    def invoke
      @singleton = nil unless defined? @singleton

      @singleton = new if @singleton.nil? or @singleton.destroyed?

      unless @singleton.visible? then
        @singleton.show_all
      else
        @singleton.destroy
      end
    end
  end


  class MainWindow < Gtk::Window
    include MainWindowSingleton
    extend DestroyWindowSpec

    def initialize(title)
      super title

      @destroyed = false
      signal_connect "destroy" do destroy end
    end

    def destroy
      Gtk.main_quit
    end
  end


end
