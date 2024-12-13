if [ $PUSH ]; then
  echo './gradlew :api:test --args=--spring.profiles.active=${ACTIVE_PROFILE} --app.mysql.server.url=${MYSQL_SERVER_URL} --app.redis.server.url=${REDIS_SERVER_URL} --app.kafka.server.url=${KAFKA_SERVER_URL}'
else
  echo './gradlew :api:bootRun --args=--spring.profiles.active=${ACTIVE_PROFILE} --app.mysql.server.url=${MYSQL_SERVER_URL} --app.redis.server.url=${REDIS_SERVER_URL} --app.kafka.server.url=${KAFKA_SERVER_URL}'
fi