package com.example.buserve.src.bus.repository;

import com.example.buserve.src.bus.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StopRepository extends JpaRepository<Stop, String> {

    // H2 동작X , MariaDb용 쿼리
    @Query(value = "SELECT * FROM stop WHERE ST_Distance_Sphere(POINT(lon, lat), POINT(:inputLon, :inputLat)) <= :distance", nativeQuery = true)
    List<Stop> findWithinDistance(@Param("inputLat") double lat, @Param("inputLon") double lon, @Param("distance") double distance);
}


