package com.pluralsight.sneakerdrop.sneakerdrops.service;

import com.pluralsight.sneakerdrop.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrop.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Sneaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SneakerService {

    private final SneakerRepository sneakerRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public SneakerService(SneakerRepository sneakerRepository, BrandRepository brandRepository) {
        this.sneakerRepository = sneakerRepository;
        this.brandRepository = brandRepository;
    }

    public long countSneakers(){
        return sneakerRepository.count();
    }

    public long countBrands(){
        return brandRepository.count();
    }

    public List<Sneaker> allSneakers(){
        return sneakerRepository.findAll();
    }

    public List<Brand> allBrands(){
        return brandRepository.findAll();
    }

    public List<Sneaker> findByYear(int year){
        return sneakerRepository.findSneakerByReleaseYear(year);
    }

    public List<Sneaker> findByContains(String input){
        return sneakerRepository.findSneakerByModelContaining(input);
    }

    public List<Sneaker> findPriceGreaterThan(double price){
        return sneakerRepository.findSneakerByPriceGreaterThan(price);
    }

    public Sneaker findById(long input){
        return sneakerRepository.findById(input).orElse(null);
    }

    public List<Sneaker> search(double price, int year){
        return sneakerRepository.search(price, year);
    }

    public void save(String model, double price, int year, Brand brand){
        sneakerRepository.save(new Sneaker(model, price, year, brand));
    }

    public void save(Sneaker sneaker){
        sneakerRepository.save(sneaker);
    }

    public void delete(Sneaker sneaker){
        sneakerRepository.delete(sneaker);
    }

    public List<Sneaker> searchBrand(Long id){
        return sneakerRepository.findSneakerByBrand_Id(id);
    }

    public List<Brand> findAllBrands(){
        return brandRepository.findAll();
    }

    public Brand findBrandById(long input){
       return brandRepository.findById(input).orElse(null);
    }

    public Brand saveBrand(Brand brand){
        return brandRepository.save(brand);
    }


}
