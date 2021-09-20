package com.obamax.ajo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "contribution")
public class Contribution extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contribution_cycle_id")
    private ContributionCycle contributionCycle;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "date_paid")
    private Date datePaid;
}
