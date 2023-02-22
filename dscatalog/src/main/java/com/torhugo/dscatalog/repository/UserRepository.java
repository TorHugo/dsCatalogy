package com.torhugo.dscatalog.repository;

import com.torhugo.dscatalog.model.entities.ProductModel;
import com.torhugo.dscatalog.model.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail (String email);
}
