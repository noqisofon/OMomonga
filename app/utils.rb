# -*- coding: utf-8 -*-
=begin
  utils.rb にはおばけももんがで使用されるユーティリィティが詰まっています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'twitter'
require 'oauth'

require File.expand_path( "app/ghostly_require.rb" )

extend OMomonga

ghostly_require 'app/*'
ghostly_require 'app/gui/*'
