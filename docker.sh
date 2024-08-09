docker run -it --name commerce-api-mysql -e MYSQL_ROOT_PASSWORD=1 -p 3306:3306 -d mysql

docker run -it --name commerce-api-redis -p 6379:6379 -d redis