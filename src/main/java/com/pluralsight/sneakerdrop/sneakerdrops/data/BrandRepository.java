package com.pluralsight.sneakerdrop.sneakerdrops.data;

import com.pluralsight.sneakerdrop.sneakerdrops.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findBrandById(Long id);
}
