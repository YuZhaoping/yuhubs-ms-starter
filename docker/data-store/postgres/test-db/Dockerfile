FROM postgres:alpine

# run create.sql on init
COPY sqls /docker-entrypoint-initdb.d/sqls
COPY init-db.sh /docker-entrypoint-initdb.d
