## How to run
1. Need to start redis server first, because it needs for tests:
```docker-compose up -d redis```
2. Then need to run tests and build jar file:
```mvnw clean package```
3. And run application:
```docker-compose up app```

The application will listen port 5000. So, the list of the urls will be looks like:
1. POST localhost:5000/publish
2. GET localhost:5000/getLast
3. GET localhost:5000/getByTime
