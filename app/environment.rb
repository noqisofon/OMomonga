# -*- coding: utf-8 -*-
=begin
  environment.rb にはおばけももんが用の環境変数を定義するモジュール Environment が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

ghostly_require :app, 'config'


module OMomonga::Environment
  # 
  # アプリケーション名を返します。
  # 
  def app_name
    Config::APPLICATION_NAME
  end

  # 
  # ユーザーの設定用ファイルのパスを返します。
  # 
  def user_config_dir
    File.join "~", ".config", app_name.downcase
  end

  # 
  # ユーザーの一時的ファイルのパスを返します。
  # 
  def user_cache_dir
    File.join "~", ".cache", app_name.downcase
  end
end
