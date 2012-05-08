# -*- coding: utf-8 -*-
=begin
  environment.rb にはおばけももんが用の環境変数を定義するモジュールを Environment が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

ghostly_require '', 


module OMomonga::Environment
  APP_NAME = Config::APPLICATION_NAME
  TMPDIR = Config::TEMPORARY_PATH
end
