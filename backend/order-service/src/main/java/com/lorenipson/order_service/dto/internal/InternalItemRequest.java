package com.lorenipson.order_service.dto.internal;

import com.lorenipson.order_service.dto.request.AddonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalItemRequest {

    private String itemType;
    private Long itemId;
    private Long sizeId;
    private Long doughId;
    private List<AddonRequest> addons;

}
