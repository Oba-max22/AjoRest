package com.obamax.ajo.repositories;

import com.obamax.ajo.models.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
}
