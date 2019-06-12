FROM tomcat:8.0.20-jre8
EXPOSE 8080
RUN rm -fr /usr/local/tomcat/webapps/*
COPY target/shopping-cart-1.0.war /usr/local/tomcat/webapps/shopping-cart.war
# COPY target/shopping-cart-1.0.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]

# docker build -t tomcat-war .
# docker run -it --rm -p 8380:8080 tomcat-war