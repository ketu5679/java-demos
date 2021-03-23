## ref https://www.jianshu.com/p/7ecb57a3f326/
1. 应用开启依赖
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```  
2. application.properties
```properties
spring.security.user.name=admin
spring.security.user.password=ketu
```  
3. 启动应用 
访问 http://localhost:8081/actuator/prometheus  
4. Prometheus 采集 Spring Boot 指标数据  
`docker pull prom/prometheus`  
然后编写 `prometheus.yml`  
```yaml
global:
  scrape_interval: 10s
  scrape_timeout: 10s
  evaluation_interval: 10m
scrape_configs:
  - job_name: spring-boot
    scrape_interval: 5s
    scrape_timeout: 5s
    metrics_path: /application/prometheus
    scheme: http
    basic_auth:
      username: user
      password: pwd
    static_configs:
      - targets:
        - 127.0.0.1:8080  #此处填写 Spring Boot 应用的 IP + 端口号
```
1. 启动应用
```bash
$ docker run -d \
--name prometheus \
-p 9090:9090 \
-m 500M \
-v "$(pwd)/prometheus.yml":/prometheus.yml \
-v "$(pwd)/data":/data \
prom/prometheus \
-config.file=/prometheus.yml \
-log.level=info
```
访问 http://localhost:9090/targets  

1. Grafana 可视化监控数据
`$ docker pull grafana/grafana` 
```bash
$ docker run --name grafana -d -p 3000:3000 grafana/grafana
``` 
http://localhost:3000/

Grafana 登录账号 admin 密码 admin


