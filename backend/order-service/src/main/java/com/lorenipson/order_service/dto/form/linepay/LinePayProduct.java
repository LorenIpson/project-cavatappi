package com.lorenipson.order_service.dto.form.linepay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePayProduct {

    private String name;
    private int quantity;
    private int price;

}
