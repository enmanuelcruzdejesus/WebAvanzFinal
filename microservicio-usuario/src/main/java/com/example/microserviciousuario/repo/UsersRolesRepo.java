package com.example.microserviciousuario.repo;

import com.example.microserviciousuario.entity.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRolesRepo extends JpaRepository<UsersRoles,Long> {

}
