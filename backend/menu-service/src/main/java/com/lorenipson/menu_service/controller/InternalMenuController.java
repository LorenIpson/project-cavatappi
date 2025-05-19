package com.lorenipson.menu_service.controller;

import com.lorenipson.menu_service.dto.retrieve.InternalItemRequest;
import com.lorenipson.menu_service.dto.retrieve.ItemSnapshotResponse;
import com.lorenipson.menu_service.service.InternalMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 給內部其它微服務呼叫的 APIs。<br>
 */
@RestController
public class InternalMenuController {

    private final InternalMenuService internalMenuService;

    public InternalMenuController(InternalMenuService internalMenuService) {
        this.internalMenuService = internalMenuService;
    }

    @PostMapping("/api/menu/internal/getItemSnapshot")
    public ResponseEntity<List<ItemSnapshotResponse>> getAllItems(@RequestBody List<InternalItemRequest> requests) {
        List<ItemSnapshotResponse> itemsDetails = internalMenuService.getItemsDetails(requests);
        return ResponseEntity.ok(itemsDetails);
    }

}
