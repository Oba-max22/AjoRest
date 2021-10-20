package com.obamax.ajo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "member_contribution_cycle")
public class MemberContributionCycle extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contribution_cycle_id")
    private ContributionCycle contributionCycle;

    private int slot;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "date_joined")
    private String dateJoined;

    @Column(name = "date_paid")
    private String datePaid;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
}
