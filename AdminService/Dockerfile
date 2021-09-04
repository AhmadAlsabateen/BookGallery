FROM openjdk:8-jre
MAINTAINER David Flemström <dflemstr@spotify.com>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.war"]

# Add Maven dependencies (not shaded into the artifact; Docker-cached)
ADD target/lib  /usr/share/myservice/lib
# Add the service itself
ADD target/AdminService-1.0-SNAPSHOT.war    /usr/share/myservice/myservice.war