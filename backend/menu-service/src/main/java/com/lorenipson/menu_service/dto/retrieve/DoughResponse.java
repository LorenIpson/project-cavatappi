package com.lorenipson.menu_service.dto.retrieve;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoughResponse {

    private Long doughId;
    private String doughType;
    private BigDecimal extraPrice;

}
