package org.example.springcloudapigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudApigatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApigatewayServiceApplication.class, args);
    }
}
