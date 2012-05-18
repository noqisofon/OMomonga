# -*- coding: utf-8 -*-
=begin
  ghostly_require.rb ではおばけももんが用の内部 require を定義しています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end


def ghostly_require(*args)
  OMomonga::GhostlyRequireSpec.ghostly_require *args
end


module OMomonga


  module GhostlyRequireSpec
    class << self

      LOCAL_PATH_CONVERTOR = {}
      LOCAL_PATH_CONVERTOR[:app] = File.dirname __FILE__
      LOCAL_PATH_CONVERTOR[:gui] = File.join LOCAL_PATH_CONVERTOR[:app], "gui"

      def local_file_require(*args)
        case args.size
          when 1
          path = args.pop

          when 2
          base, file = args[0..1]
          key = base.to_sym
          if LOCAL_PATH_CONVERTOR.has_key? key then
            base = LOCAL_PATH_CONVERTOR[key]
          end
          #p base
          path = File.join( base, file )
        end
        #p "local_file_require by #{path}"
        require path if File.exist? path
      end

      def files_or_directory_require(path)
        key = File.split( path ).pop.to_sym
        if LOCAL_PATH_CONVERTOR.has_key? key then
          Dir.chdir LOCAL_PATH_CONVERTOR[key] do |current_path|
            Dir.glob( "*.rb" ).each { |rbfile|
              local_file_require File.join( current_path, rbfile )
            }
          end
        end
      end

      def ghostly_require(*args)
        case args.size
          when 1
          path = args.pop
          if File.basename( path ) == '*' then
            files_or_directory_require File.dirname( path )
          else
            local_file_require *File.split( path )
          end

          else
        end
      end

    end
  end


end
