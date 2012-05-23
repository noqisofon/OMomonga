# -*- coding: utf-8 -*-
=begin
  auth-box.rb はついったーにログインするためのダイアログが入っています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'gtk2'
require 'oauth'

require File.expand_path( "../ghostly_require.rb", File.dirname( __FILE__ ) )

ghostly_require 'app/twitter-config.rb'
ghostly_require 'app/utils/model.rb'


module OMomonga


  class AuthBox < Gtk::Window
    def initialize
      super "ついったー認証"

      # コンシューマーキーとコンシューマシークレット文字列を渡して、コンシューマオブジェクトを作成します。
      consumer = OMomonga::TwitterConfig.consumer

      # 作成したコンシューマから、リクエストトークンを取得します。
      request_token = consumer.get_request_token

      @label = Gtk::Label.new
      @hbox0 = Gtk::HBox.new
      @pin_entry = Gtk::Entry.new
      @auth_button = Gtk::Button.new( "認証", false )
      @vbox0 = Gtk::VBox.new

      #
      # @label
      #
      @label.markup = <<TEXT
こんばんは。
現在、あなたはおばけももんがにおいて
Twitter アカウントを認証していません。
以下の URL をお使いのブラウザで開き、おばけももんがの
認証を行なってください。

<a href="#{request_token.authorize_url}">#{request_token.authorize_url}</a>
TEXT
      #
      # @hbox0
      #
      @hbox0.pack_start @pin_entry
      @hbox0.pack_start @auth_button

      #
      # @pin_entry
      #

      #
      # @auth_button
      #
      @auth_button.signal_connect "clicked" do auth_button_clicked request_token end

      #
      # @vbox0
      #
      @vbox0.pack_start @label
      @vbox0.pack_start @hbox0

      #
      # self
      #
      signal_connect "destroy" do destroyed end
      set_default_size 300, 80
      set_border_width 10
      set_destroy_with_parent true
      resizeable = false
      focus = @pin_entry
      add @vbox0
    end

    #
    #
    #
    def auth_button_clicked(request_token)
      pin_entry_text = @pin_entry.text
      unless pin_entry_text.length == 0 then
        access_token = request_token.get_access_token( :oauth_token => request_token.token,
                                                 :oauth_verifier => pin_entry_text )

        Utils.save_access_token access_token

        Gtk.main_quit
      end
    end

    def destroyed
      Gtk.main_quit if transient_for.nil?
    end

  end


end


if $0 == __FILE__ then
  auth_box = OMomonga::AuthBox.new
  auth_box.show_all

  Gtk.main
end
