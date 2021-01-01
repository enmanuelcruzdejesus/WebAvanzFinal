package com.example.microserviciousuario.repo;

import com.example.microserviciousuario.entity.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepo extends JpaRepository<AppSetting,Integer> {
}
