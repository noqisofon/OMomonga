# -*- coding: utf-8 -*-
=begin
  ghostly_require.rb ではおばけももんが用の内部 require を定義しています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end


module OMomonga


  def ghostly_require(*args)
    GhostlyRequire.require *args
  end


end
