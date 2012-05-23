# -*- coding: utf-8 -*-
=begin
  about-box.rb はついったーにログインするためのダイアログが入っています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end
require 'gtk2'
require File.expand_path( "../ghostly_require.rb", File.dirname( __FILE__ ) )

ghostly_require "app/config.rb"
ghostly_require "app/version.rb"
ghostly_require "app/gui/main-window.rb"


module OMomonga::Gui


  class AboutBox < ::Gtk::Dialog
    def initialize(parent = nil)
      super "#{OMomonga::Config::APPLICATION_NAME} について", parent, Gtk::Dialog::MODAL | Gtk::Dialog::DESTROY_WITH_PARENT

      appname_label = Gtk::Label.new
      appname_label.markup = "<big>#{OMomonga::Config::APPLICATION_NAME}</big>"

      version_label = Gtk::Label.new
      version_label.text = OMomonga::VERSION.inspect

      vbox.pack_start appname_label
      vbox.pack_start version_label

      add_button Gtk::Stock::OK, Gtk::Dialog::RESPONSE_OK
      set_default_size 300, 120
    end
  end

end


if $0 == __FILE__ then
  about_box = OMomonga::Gui::AboutBox.new
  about_box.show_all
  about_box.run
  about_box.destroy
end
