package com.obamax.ajo.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "request")
public class Request extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "date_of_request")
    private LocalDate dateOfRequest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contribution_cycle_id")
    private ContributionCycle contributionCycle;

    @Column(name = "status_of_request", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestStatusType statusOfRequest;
}
