#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
=begin
  omomonga.rb は エントリーポイントです。omomonga の o は obake の o です。

  Copyright (C) 2012 ned rihine All rights reserved.
=end

require 'optparse'


module OMomonga

  class Application
    PROGRAM = File.basename __FILE__, ".*"

    WARNING_LEVEL = {
      :silence => 1000,
      :medium => 500,
      :debug => 200,
      :verbose => 100
    }

    #
    #
    #
    def initialize(app_name = PROGRAM, application_specific = {}, *arguments)
      @options = {}
      @warning_level = :midium
    end  # initialize

    #
    #
    #
    def run
      argument_parse
      argument_verify

      require File.expand_path( "utils" )
    end  # run

    private
    # 
    # コマンドオプションをパースします。
    # 
    def argument_parse
      option_parser = OptionParser.new do |opts|
        #opts.banner = HELP_TEXT
        # 
        # YAML 形式の authrize token が書かれたファイルを指定します。
        # 
        opts.on "-a", "--account PATH", "アーサライズトークンをファイルで指定する" do |account_file|
          @options[:account] = account_file
        end
        # 
        # 処理情報をコンソールに全く出力しないようにします。
        # 
        opts.on "-q", "--quiet", "処理情報を出力しない" do |quiet_flag|
          @options[:quiet] = quiet_flag
        end
        # 
        # 処理情報をコンソールに過剰に出力するようにします。
        # 
        opts.on "--verbose", "処理情報を過剰に出力する" do |verbose_flag|
          @options[:verbose] = verbose_flag
        end
        # 
        # デバッグモードで起動します。
        # 
        opts.on "--debug", "デバッグモードで起動" do |debug_flag|
          @options[:debug] = debug_flag
        end
      end

      args = ARGV.clone

      option_parser.parse args
    end  # argument_parse

    # 
    # パースしたコマンドオプションの整合性や正しさをチェックします。
    # 
    def argument_verify
      if @options.has_key? :account then
        @account_filepath = File.expand_path @options[:account]
        unless File.exist? @account_filepath then
          $stderr.puts "#{PROGRAM}: cannot access #{@options[:account]}: No such file or directory"

          exit 0
        end
      end

      if @options.has_key? :debug then
        @warning_level = :debug
      end
    end  # argument_verify

    #
    #
    #
    def load_account_file
      
    end  # load_account_file
  end  # Application


end  # OMomonga


if $0 == __FILE__ then
  progn = OMomonga::Application.new
  progn.run
end
