<?xml version='1.0' encoding='ISO-8859-1' ?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN" "http://java.sun.com/products/javahelp/helpset_2_0.dtd">

<!-- this file specifies the names and locations of the files comprising the helpset -->
<helpset version="2.0">
     <title>Beaglebuddy Tab Editor Help</title>
     <maps>
          <mapref location="map.jhm"/>
          <homeID>intro</homeID>
     </maps>
     <view>
          <name>TOC</name>
          <label>Table of Contents</label>
          <type>javax.help.TOCView</type>
          <data>toc.xml</data>
     </view>
     <view>
          <name>Index</name>
          <label>Index</label>
          <type>javax.help.IndexView</type>
          <data>index.xml</data>
     </view>
     <view>
          <name>Search</name>
          <label>Search</label>
          <type>javax.help.SearchView</type>
          <data engine="com.sun.java.help.search.DefaultSearchEngine">JavaHelpSearch</data>
     </view>
</helpset>
