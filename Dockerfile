# Dockerfile
FROM registry.access.redhat.com/jboss-webserver-3/webserver30-tomcat8-openshift
EXPOSE 8090 8090
ADD target/*.war /usr/local/tomcat/webapps/
