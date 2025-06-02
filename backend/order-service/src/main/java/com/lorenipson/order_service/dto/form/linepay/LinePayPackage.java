package com.lorenipson.order_service.dto.form.linepay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinePayPackage {

    private String id;
    private String name;
    private int amount;
    private List<LinePayProduct> products;

}
