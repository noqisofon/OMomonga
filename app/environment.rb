# -*- coding: utf-8 -*-
=begin
  environment.rb にはおばけももんが用の環境変数を定義するモジュール Environment が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'fileutils'

require File.expand_path( "./ghostly_require.rb", File.dirname( __FILE__ ) )

ghostly_require 'app/config.rb'


module OMomonga::Environment
  class << self
    #
    # アプリケーション名を返します。
    #
    def app_name
      OMomonga::Config::APPLICATION_REGISTATION_NAME
    end

    #
    # ユーザーの設定用ファイルのパスを返します。
    #
    def user_config_dir
      path = File.expand_path( File.join( "~", ".config", app_name.downcase ) )
      FileUtils.mkdir_p path unless File.exist? path

      path
    end

    #
    # ユーザーの一時的ファイルのパスを返します。
    #
    def user_cache_dir
      path = File.expand_path( File.join( "~", ".cache", app_name.downcase ) )
      FileUtils.mkdir_p path unless File.exist? path

      path
    end
  end
end


if $0 == __FILE__ then
  p OMomonga::Environment.app_name
  p OMomonga::Environment.user_config_dir
  p OMomonga::Environment.user_cache_dir
end
