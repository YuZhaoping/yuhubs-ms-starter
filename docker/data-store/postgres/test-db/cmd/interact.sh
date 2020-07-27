#!/usr/bin/env bash

cd $(dirname $0) && cd ../

echo "PGPASSWORD='u123@Yuhubs'"
docker run -it --rm --network=ecom-backend \
  -v "$(pwd)":/mnt/db:ro \
  postgres:alpine \
  psql -h test-pg-db -U postgres --password
