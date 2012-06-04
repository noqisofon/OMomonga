# -*- coding: utf-8 -*-
=begin
  model.rb はおばけももんがで使用するモデルを扱うクラスやメソッドが入っています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'yaml'
require 'oauth'
require 'twitter'

require File.expand_path( "../ghostly_require.rb", File.dirname( __FILE__ ) )

ghostly_require 'app/environment.rb'
ghostly_require 'app/twitter-config.rb'


module OMomonga::Utils

  class << self

    #
    #
    #
    def save_access_token(access_token)
      #
      # アカウントのスクリーンネームを取得するために Twitter の設定を行います。
      #
      Twitter.configure do |config|
        config.consumer_key = OMomonga::TwitterConfig::CONSUMER_KEY
        config.consumer_secret = OMomonga::TwitterConfig::CONSUMER_SECRET
        config.oauth_token = access_token.token
        config.oauth_token_secret = access_token.secret
      end
      #
      # 現在のユーザ、つまり access_token を得たユーザオブジェクトを取得します。
      #
      user = Twitter.current_user

      filepath = get_access_token_file user.screen_name
      File.open( filepath, "w" ) do |input|
        YAML.dump( { "token" => access_token.token,
                     "secret" => access_token.secret },
             input )
      end
    end

    #
    #
    #
    def load_access_token(account_name)
      filepath = get_access_token_file account_name
      #
      # 指定されたファイルがない場合、nil を返します。
      #
      return nil unless File.exist? filepath

      File.open( filepath, "r" ) { |input| YAML.load input }
    end

    private
    #
    # 指定されたアカウント名からアクセストークンを保存するファイルのパスを返します。
    #
    def get_access_token_file(account_name)
      File.join OMomonga::Environment.user_config_dir, "#{account_name}.yml"
    end

  end  # class << self

end  # module OMomonga::Utils


if $0 == __FILE__ then
  p OMomonga::Utils.load_access_token ARGV[0] if ARGV.size > 0
end
