package com.example.dataanalyze.models.stat.output;

import lombok.Data;

import java.util.List;

@Data
public class PurchasesDTO {
    private String name;
    private List<ProductStatDTO> expenses;

}
