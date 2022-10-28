# dist-systems-task

## ping-pong
**The first service** sends ping events to **the second service**. It receives events and replies to them by sending pong events back. 

### how to build
```sh
./gradlew bootJar
```

### how to run
```sh
docker-compose up
```