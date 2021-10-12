package com.obamax.ajo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContributionCycleDTO {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate paymentStartDate;

    private LocalDate paymentEndDate;

    private Double monthlyAmount;
}
