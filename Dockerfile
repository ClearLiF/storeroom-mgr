FROM java:8
VOLUME /tmp
ADD /target/storeroom-0.0.1-SNAPSHOT.jar storeroom.jar
EXPOSE 8081
ENTRYPOINT ["java","-Dfile.encoding=utf-8","-Dsun.jnu.encoding=UTF8","-Xmx256m","-Xms256m","-jar","/storeroom.jar"]
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone