FROM public.ecr.aws/bitnami/java:1.8

RUN apt-get update && apt-get install -y nocache wget jq bash figlet

RUN mkdir -p /data/backend

COPY ./src/main/resources/ /data/backend/
COPY ./build/run.sh /run.sh

RUN chmod 644 /data/backend/* && \
    mkdir -p /data/backend/log && \
    chmod 755 /run.sh

VOLUME /tmp

CMD ["/run.sh"]

COPY ./target/backend-retrieve-products.jar /data/backend/backend-retrieve-products.jar

EXPOSE 8080