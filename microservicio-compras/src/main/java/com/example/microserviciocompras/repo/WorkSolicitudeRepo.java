package com.example.microserviciocompras.repo;

import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.entity.WorkByStatus;
import com.example.microserviciocompras.entity.WorkSolicitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSolicitudeRepo extends JpaRepository<WorkSolicitude,Integer> {

    @Query("SELECT " +
            "    new com.example.microserviciocompras.entity.WorkByStatus(v.status, COUNT(v)) " +
            "FROM " +
            "    WorkSolicitude v " +
            "GROUP BY " +
            "    v.status")
    List<WorkByStatus> findStatusCount();
}
