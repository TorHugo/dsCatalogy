package com.torhugo.dscatalog.repository;

import com.torhugo.dscatalog.model.entities.RoleModel;
import com.torhugo.dscatalog.model.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
}
