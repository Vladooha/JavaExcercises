package com.vladooha.data.repo;

import com.vladooha.data.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepo extends JpaRepository<Car, Long> {
    Car findByCustomId(long customId);
    @Query("SELECT COUNT(DISTINCT c.vendor) FROM Car c")
    long countByVendor();
}
