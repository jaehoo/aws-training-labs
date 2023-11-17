FROM public.ecr.aws/bitnami/java:1.8

RUN apt-get update && apt-get install -y nocache wget jq bash figlet

RUN mkdir -p /data/frontend

COPY ./src/main/resources/ /data/frontend/
COPY ./build/run.sh /run.sh

RUN chmod 644 /data/frontend/* && \
    mkdir -p /data/frontend/log && \
    chmod 755 /run.sh

VOLUME /tmp

CMD ["/run.sh"]

COPY ./target/frontend-ui-products.jar /data/frontend/frontend-ui-products.jar

EXPOSE 8080