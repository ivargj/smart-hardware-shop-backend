# Smart Hardware Shop

This application can be run locally, and the /products endpoint can be accessed through localhost:8080/products. For now
it supports POST and GET. GET /products will get all products and GET products/{id} gets a specific product.

## MySQL in Docker

`application.yml` already contains connection information to this DB.

```shell script
docker-compose up -d
```

