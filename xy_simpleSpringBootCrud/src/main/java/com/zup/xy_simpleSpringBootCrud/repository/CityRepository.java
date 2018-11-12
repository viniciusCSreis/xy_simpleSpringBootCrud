package com.zup.xy_simpleSpringBootCrud.repository;

import com.zup.xy_simpleSpringBootCrud.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {

    Page<City> findByNameIgnoreCaseContaining(Pageable pageable,String name);

}
