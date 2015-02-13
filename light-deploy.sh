#!/bin/sh
mvn clean package
cp target/sem.war /home/giuseppe/apache-tomcat-7.0.52/webapps
