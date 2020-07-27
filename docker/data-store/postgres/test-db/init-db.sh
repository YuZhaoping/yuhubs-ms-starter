#!/usr/bin/env bash
set -e

cd $(dirname $0)

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  \i sqls/begin-create.sql

  \i sqls/end-create.sql

  \i sqls/data-init.sql
EOSQL
