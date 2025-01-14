package com.example.demo.repositories;

import com.example.demo.domain.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {
    List<District> findByProvinceCode(String provinceCode);

    District findByCode(String code);
}
