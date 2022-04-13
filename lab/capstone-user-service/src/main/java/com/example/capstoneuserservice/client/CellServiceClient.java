package com.example.capstoneuserservice.client;

import com.example.capstoneuserservice.vo.ResponseCell;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "capstone-cell-service")
public interface CellServiceClient {

    @GetMapping("/{userId}/cells")
    List<ResponseCell> getCells(@PathVariable String userId);

    // delete users 하면 userId에 해당하는 모든 cell 을 delete 함
    @DeleteMapping("/{userId}")
    void deleteCells(@PathVariable String userId);

    // cellId에 해당하는 cell을 delete함
    @DeleteMapping(value = {"/{userId}/{cellId}"})
    void deleteCell(@PathVariable("userId") String userId, @PathVariable("cellId") String cellId);

}