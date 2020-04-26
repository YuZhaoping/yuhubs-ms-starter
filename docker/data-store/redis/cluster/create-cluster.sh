#!/usr/bin/env bash

cd $(dirname $0)


for port in `seq 7000 7005`; do \
  docker run -d -p ${port}:${port} -p 1${port}:1${port} \
  -v $(pwd)/deploy/${port}/conf/redis.conf:/usr/local/etc/redis/redis.conf \
  -v $(pwd)/deploy/${port}/data:/data \
  --name redis-${port} --net redis-net \
  --sysctl net.core.somaxconn=1024 \
  redis:alpine \
  redis-server /usr/local/etc/redis/redis.conf; \
done


function get_container_ip() {
  docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' redis-$1
}


REDIS_CLI_CMD="redis-cli --cluster create"

for port in `seq 7000 7005`; do \
  REDIS_CLI_CMD="${REDIS_CLI_CMD} $(get_container_ip ${port}):${port}"; \
done

REDIS_CLI_CMD="${REDIS_CLI_CMD} --cluster-replicas 1"


docker exec -it redis-7000 sh -c \
"echo '${REDIS_CLI_CMD}' && ${REDIS_CLI_CMD}"
