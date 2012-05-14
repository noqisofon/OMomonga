# -*- coding: utf-8 -*-
=begin
  ghostly_require.rb ではおばけももんが用の内部 require を定義しています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end


module OMomonga


  def ghostly_require(*args)
    case args.size
    when 1
      path = args.pop
    end
    if File.basename( path ) == '*' then
      # ベースネームがアスタリスクの場合、ディレクトリ内の全ての Ruby ファイルを require します。
      Dir.chdir( File.expand_path File.dirname( path ) ) do |current_path|
        Dir.glob( "*.rb" ).each { |rbfile| require File.join( current_path, rbfile ) }
      end
    else
      require File.expand_path path
    end
  end


end
