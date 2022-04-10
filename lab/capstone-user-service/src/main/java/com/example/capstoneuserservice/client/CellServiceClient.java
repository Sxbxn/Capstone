package com.example.capstoneuserservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cell-service")
public interface CellServiceClient {

//    @GetMapping("/order-service/{userId}/orders")
//    List<ResponseOrder> getOrders(@PathVariable String userId);
}
