# -*- coding: utf-8 -*-
=begin
  twitter-config.rb には…おいこらやめろ！ このファイルは見ちゃらめぇぇぇぇぇぇぇぇぇぇぇぇぇぇ！！！

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'oauth'

require File.expand_path( "./ghostly_require.rb", File.dirname( __FILE__ ) )


module OMomonga::TwitterConfig
  CONSUMER_KEY = 'f0tDONnPe3998s4bIb0P5Q'
  CONSUMER_SECRET = 'B5ecg0mapSqUfC5y6PQbHxtUqRZMBkln0RhFkhs'

  class << self
    def consumer
      @@consumer = OAuth::Consumer.new( CONSUMER_KEY, CONSUMER_SECRET, :site => 'http://api.twitter.com' ) unless defined? @@consumer

      @@consumer
    end
  end
end
