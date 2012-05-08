# -*- coding: utf-8 -*-
=begin
  version.rb はおばけももんがのバージョン情報が含まれています。

  Copyright (C) 2012 ned rihine All rights reserved.
=end


module OMomonga


  class Version
    MAJOR = 0
    MINOR = 0
    REVISION = 1

    attr_reader :major, :minor, :revision

    def initialize(major = MAJOR, minor = MINOR, revision = REVISION)
      @major = major
      @minor = minor
      @revision = revision
    end

    def to_a
      [ @major, @minor, @revision ]
    end

    def to_f
      @major + (@minor.to_f / 100.f)
    end

    def to_i
      @major
    end

    # X.Y.Z のような書式のバージョン番号用文字列を返します。
    def to_s
      to_a.join "."
    end

    def inspect
      "#{Environment::APPNAME} ver #{to_s}"
    end

    def size
      3
    end

    def length
      3
    end

    def <=>(other)
      self.to_a <=> other.to_a if other.size == 3
    end
  end


  VERSION = Version.new


end
