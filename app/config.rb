# -*- coding: utf-8 -*-
=begin
  config.rb にはおばけももんが用のデフォルト設定を定義するモジュールが含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require File.expand_path( "./ghostly_require.rb", File.dirname( __FILE__ ) )


module OMomonga::Config
  #
  # アプリケーション名の英語バージョン。
  #
  APPLICATION_REGISTATION_NAME = "OMomonga"
  #
  # 本来のアプリケーション名。
  #
  APPLICATION_NAME = "おばけももんが"
end


if $0 == __FILE__ then
  p OMomonga::Config::APPLICATION_REGISTATION_NAME
  p OMomonga::Config::APPLICATION_NAME
end
