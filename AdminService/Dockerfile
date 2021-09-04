FROM tomcat:8.0
MAINTAINER Ahmad_Sabateen

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.war"]

COPY target/AdminService-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
