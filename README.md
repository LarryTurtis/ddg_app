### DDG

How to deploy.

```
docker build -t rest-service .
docker run --tmpfs /tmp -p 8080:8080 rest-service
```
