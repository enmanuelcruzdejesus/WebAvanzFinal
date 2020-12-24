package com.example.microserviciocompras.repo;

import com.example.microserviciocompras.entity.AppSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingRepo extends JpaRepository<AppSetting,Integer> {
}
