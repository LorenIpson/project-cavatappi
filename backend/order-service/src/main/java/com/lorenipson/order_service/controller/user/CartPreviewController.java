package com.lorenipson.order_service.controller.user;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import com.lorenipson.order_service.dto.internal.InternalItemSnapshotResponse;
import com.lorenipson.order_service.service.cart.CartPreviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 使用者購物車預覽商品資訊。
 */
@RestController
public class CartPreviewController {

    private final CartPreviewService cartPreviewService;

    public CartPreviewController(CartPreviewService cartPreviewService) {
        this.cartPreviewService = cartPreviewService;
    }

    /**
     * <code>Internal</code> 取得商品資訊。
     */
    @PostMapping("/api/order/cart/preview")
    public ResponseEntity<?> getCartPreview(@RequestBody List<InternalItemRequest> requests) {
        List<InternalItemSnapshotResponse> response = cartPreviewService.getCartPreview(requests);
        return ResponseEntity.ok(response);
    }

}
