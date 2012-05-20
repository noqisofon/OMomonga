# -*- coding: utf-8 -*-
=begin
  auth-box.rb はついったーにログインするためのダイアログが入っています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'gtk2'
require 'oauth'
require 'twitter'
require 'yaml'

require File.expand_path( "../ghostly_require.rb", File.dirname( __FILE__ ) )

ghostly_require 'app/environment.rb'
ghostly_require 'app/twitter-config.rb'


module OMomonga


  class AuthBox < Gtk::Window
    def initialize
      super "ついったー認証"

      # コンシューマーキーとコンシューマシークレット文字列を渡して、コンシューマオブジェクトを作成します。
      consumer = OMomonga::TwitterConfig.consumer

      # 作成したコンシューマから、リクエストトークンを取得します。
      request_token = consumer.get_request_token

      label = Gtk::Label.new
      hbox0 = Gtk::HBox.new
      pin_entry = Gtk::Entry.new
      auth_button = Gtk::Button.new( "認証", false )
      vbox0 = Gtk::VBox.new

      #
      # label
      #
      label.markup = <<TEXT
こんばんは。
現在、あなたはおばけももんがにおいて
Twitter アカウントを認証していません。
以下の URL をお使いのブラウザで開き、おばけももんがの
認証を行なってください。

<a href="#{request_token.authorize_url}">#{request_token.authorize_url}</a>
TEXT
      #
      # hbox0
      #
      hbox0.pack_start pin_entry
      hbox0.pack_start auth_button

      #
      # pin_entry
      #

      #
      # auth_button
      #
      auth_button.signal_connect "clicked" do
        unless pin_entry.text.length == 0 then
          access_token = request_token.get_access_token( :oauth_token => request_token.token,
                                                  :oauth_verifier => pin_entry.text )
          Twitter.configure do |config|
            config.consumer_key = OMomonga::TwitterConfig::CONSUMER_KEY
            config.consumer_secret = OMomonga::TwitterConfig::CONSUMER_SECRET
            config.oauth_token = access_token.token
            config.oauth_token_secret = access_token.secret
          end
          user = Twitter.current_user
          YAML.dump( {
                       "token" => access_token.token,
                       "secret" => access_token.secret
                     },
               File.open( File.join( OMomonga::Environment.user_config_dir, "#{user.screen_name}.yml" ), "w" ) )
          Gtk.main_quit
        end
      end

      #
      # vbox
      #
      vbox0.pack_start label
      vbox0.pack_start hbox0

      #
      # self
      #
      set_default_size 300, 80
      add vbox0
    end
  end


end


if $0 == __FILE__ then
  auth_box = OMomonga::AuthBox.new
  auth_box.show_all

  Gtk.main
end
