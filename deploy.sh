#!/bin/sh

kill `ps x | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{print $1}'`
rm -rf /home/giuseppe/apache-tomcat-7.0.52/webapps/sem*
rm -rf /home/giuseppe/apache-tomcat-7.0.52/conf/Catalina/*
rm -rf /home/giuseppe/apache-tomcat-7.0.52/work/*

mvn clean package
cp target/sem.war /home/giuseppe/apache-tomcat-7.0.52/webapps

sh /home/giuseppe/apache-tomcat-7.0.52/bin/catalina.sh jpda start
