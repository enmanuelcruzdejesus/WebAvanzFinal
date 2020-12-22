package com.example.microserviciousuario.repo;


import com.example.microserviciousuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo  extends JpaRepository<User,Long> {

    User findByEmail(String email);

//    @Query("SELECT u from User u WHERE u.email =:email and u.password=:password")
//    public User login(@Param("email") String username, @Param("password") String password);
//
//    @Query("SELECT u from User u WHERE u.email =:email")
//    Optional<User> findByUsername(@Param("email") String username);
}