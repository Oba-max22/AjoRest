package com.obamax.ajo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contribution_cycle")
public class ContributionCycle extends BaseEntity {

    @NotBlank
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "payment_start_date")
    private Date paymentStartDate;

    @Column(name = "payment_end_date")
    private Date paymentEndDate;

    @OneToMany(mappedBy = "contributionCycle", fetch = FetchType.LAZY)
    private List<Contribution> contributions;

    @OneToMany(mappedBy = "contributionCycle", fetch = FetchType.LAZY)
    private List<Request> requests;

}
