package com.obamax.ajo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contribution_cycle")
public class ContributionCycle extends BaseEntity {

    @NotBlank
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "payment_start_date")
    private LocalDate paymentStartDate;

    @Column(name = "payment_end_date")
    private LocalDate paymentEndDate;

    @JsonIgnore
    @OneToMany(mappedBy = "contributionCycle", fetch = FetchType.LAZY)
    private List<Contribution> contributions;

    @JsonIgnore
    @OneToMany(mappedBy = "contributionCycle", fetch = FetchType.LAZY)
    private List<Request> requests;

    @Column(name = "monthly_amount")
    private Double monthlyAmount;
}
