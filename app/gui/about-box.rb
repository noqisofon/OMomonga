# -*- coding: utf-8 -*-
require 'gtk2'


module OMomonga

  module Gui


    class AboutBox < Gtk::Dialog
      def initialize(parent = nil)
        super "おばけももんが について", parent, Gtk::Dialog::MODAL | Gtk::Dialog::DESTROY_WITH_PARENT

        appname_label = Gtk::Label.new
        appname_label.markup = "<big>おばけももんが</big>"
        vbox.pack_start appname_label

        add_button Gtk::Stock::OK, Gtk::Dialog::RESPONSE_OK
        set_default_size 300, 120
      end
    end

  end

end


if $0 == __FILE__ then
  require "../utils.rb"

  about_box = OMomonga::Gui::AboutBox.new
  about_box.show_all
  p about_box.run
  about_box.destroy
end
