package com.pluralsight.sneakerdrop.sneakerdrops.data;

import com.pluralsight.sneakerdrop.sneakerdrops.models.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SneakerRepository extends JpaRepository<Sneaker, Long> {

    List<Sneaker> findSneakerByModelContaining(String model);

    List<Sneaker> findSneakerByPriceGreaterThan(double price);

    List<Sneaker> findSneakerByReleaseYear(int year);

    List<Sneaker> findSneakerByBrand_Id(long id);

    @Query("SELECT s FROM Sneaker s  WHERE s.price <= :maxPrice AND s.releaseYear >= :minYear")
    List<Sneaker> search(double maxPrice, int minYear);
}
