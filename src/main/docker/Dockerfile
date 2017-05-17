FROM ubuntu:14.04

# Install Java 8
RUN apt-get update
RUN apt-get install software-properties-common -y
RUN add-apt-repository ppa:webupd8team/java -y
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN apt-get install oracle-java8-installer -y --force-yes
RUN apt-get install oracle-java8-set-default

#install Sci tool
RUN wget http://builds.scitools.com/all_builds/b844/Understand/Understand-4.0.844-Linux-64bit.tgz && tar -zxvf Understand-4.0.844-Linux-64bit.tgz
RUN echo "Server: scitools-license.devfactory.com 00000000 9000" > scitools/conf/license/locallicense.dat

#configure libraries
RUN apt-get install libxrender1 -y
RUN apt-get install libxi6 libgconf-2-4 -y
RUN apt-get install libxtst6 -y

ENV LD_LIBRARY_PATH /usr/lib/jvm/java-8-oracle/jre/lib/amd64:/scitools/bin/linux64
ENV PYTHONPATH /scitools/bin/linux64/python
ENV STIHOME /scitools/bin/linux64

# Configure  env
RUN mkdir app
COPY dead-code-detection-0.0.1.jar /app/api.jar

EXPOSE 8080
CMD java -jar -Dspring.profiles.active=dev -Djava.library.path=/scitools/bin/linux64:/scitools/bin/linux64/Java /app/api.jar
#ENTRYPOINT [ "sh", "-c", "java -jar -Dspring.profiles.active=dev -Djava.library.path=/scitools/bin/linux64:/scitools/bin/linux64/Java /app/api.jar" ]