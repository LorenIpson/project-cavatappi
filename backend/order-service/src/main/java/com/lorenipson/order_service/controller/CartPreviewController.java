package com.lorenipson.order_service.controller;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import com.lorenipson.order_service.dto.response.ItemSnapshotResponse;
import com.lorenipson.order_service.service.CartPreviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartPreviewController {

    private final CartPreviewService cartPreviewService;

    public CartPreviewController(CartPreviewService cartPreviewService) {
        this.cartPreviewService = cartPreviewService;
    }

    @PostMapping("/api/order/cart/preview")
    public ResponseEntity<?> getCartPreview(@RequestBody List<InternalItemRequest> requests) {
        List<ItemSnapshotResponse> response = cartPreviewService.getCartPreview(requests);
        return ResponseEntity.ok(response);
    }

}
