FROM mysql:8

COPY script.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD "2592"
ENV MYSQL_DATABASE "stock"

EXPOSE 3306
