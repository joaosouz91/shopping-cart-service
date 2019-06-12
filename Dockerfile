FROM tomcat:8.0.20-jre8
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/*
COPY target/shopping-cart-1.0.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]