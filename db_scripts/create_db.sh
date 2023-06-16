docker run --name postgres-db -e POSTGRES_DB=postgres-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v /tmp/postgres-data:/var/lib/postgresql/data -p 5001:5432 postgres:14
docker run --name pgadmin -e PGADMIN_DEFAULT_EMAIL=root@admin.org -e PGADMIN_DEFAULT_PASSWORD=pgpass -p 8888:80 dpage/pgadmin4:6.9
docker network create db-net
docker network connect db-net postgres-db
docker network connect db-net pgadmin