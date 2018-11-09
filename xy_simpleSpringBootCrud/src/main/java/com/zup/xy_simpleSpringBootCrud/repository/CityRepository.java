package com.zup.xy_simpleSpringBootCrud.repository;

import com.zup.xy_simpleSpringBootCrud.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
