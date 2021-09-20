package com.obamax.ajo.repositories;

import com.obamax.ajo.models.Role;
import com.obamax.ajo.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByType(RoleType type);
}

