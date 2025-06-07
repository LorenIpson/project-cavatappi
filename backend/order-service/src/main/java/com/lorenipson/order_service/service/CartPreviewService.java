package com.lorenipson.order_service.service;

import com.lorenipson.order_service.dto.internal.InternalItemRequest;
import com.lorenipson.order_service.dto.response.ItemSnapshotResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
/**
 * 取得前端使用者儲存的購物車詳細商品資訊。<br>
 */
@Service
public class CartPreviewService {

    @Value("${backend.menu.service.url}")
    private String backendMenuServiceURL;

    public List<ItemSnapshotResponse> getCartPreview(List<InternalItemRequest> requests) {
        return RestClient.create().post()
                .uri(backendMenuServiceURL + "/api/menu/internal/getItemSnapshot")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requests)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

}
