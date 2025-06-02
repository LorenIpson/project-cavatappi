package com.lorenipson.order_service.dto.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePayForm {

    private Long orderId;
    private String currency;
    private int amount;
    private List<LinePayPackage> packages;
    private Map<String, String> redirectUrls;

}
