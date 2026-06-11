package com.pluralsight.sneakerdrop.sneakerdrops;

import com.pluralsight.sneakerdrop.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrop.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrop.sneakerdrops.service.DropService;
import com.pluralsight.sneakerdrop.sneakerdrops.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final DropService dropService;
    private final InventoryService inventoryService;
    private final BrandRepository brandRepository;
    private final SneakerRepository sneakerRepository;

    @Autowired
    public StartupRunner(DropService dropService, InventoryService inventoryService, BrandRepository brandRepository, SneakerRepository sneakerRepository) {
        this.dropService = dropService;
        this.inventoryService = inventoryService;
        this.brandRepository = brandRepository;
        this.sneakerRepository = sneakerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(dropService.getStatus());
        System.out.println(inventoryService.getInventory());
        seedData();
        while(true) {
            System.out.println("1). List all shoes");
            System.out.println("2). Find by model");
            System.out.println("3). Find by price");
            System.out.println("4). Find by release year");
            System.out.println("5). Find by ID");
            System.out.println("6). Advance Search");
            System.out.println("7). Add shoe");
            System.out.println("8). Update Price");
            System.out.println("9). Delete shoe");
            System.out.println("10). Search by Brand");
            System.out.println("11). Brand Management");
            System.out.println("0). Quit");
            System.out.print("Input: ");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch(input){
                case 1 -> listSneakers();
                case 2 -> listSneakersByModel(scanner);
                case 3 -> listSneakerByPrice(scanner);
                case 4 -> listSneakerByYear(scanner);
                case 5 -> viewByID(scanner);
                case 6 -> advanceSearch(scanner);
                case 7 -> addShoe(scanner);
                case 8 -> updatePrice(scanner);
                case 9 -> deleteShoe(scanner);
                case 10 -> listShoesByBrand(scanner);
                case 11 -> brandManagement(scanner);
                case 0 -> {
                    return;
                }
                default -> System.out.println("invalid input");

            }
        }
    }

    private void listSneakers(){
        System.out.println("Total = " + sneakerRepository.count());
        for (Sneaker s : sneakerRepository.findAll()) {
            System.out.println(s.getId() + " - " + s.getModel());
        }
    }
    private void listSneakersByModel(Scanner scanner){
        System.out.print("Please enter model: ");
        String input = scanner.nextLine();

        for(Sneaker s : sneakerRepository.findSneakerByModelContaining(input)){
            System.out.println(s);
        }
    }
    private void listSneakerByPrice(Scanner scanner){
        System.out.print("Please enter price: ");
        double input = scanner.nextDouble();
        scanner.nextLine();

        for(Sneaker s : sneakerRepository.findSneakerByPriceGreaterThan(input)){
            System.out.println(s);
        }
    }

    private void listSneakerByYear(Scanner scanner){
        System.out.print("Please enter release year: ");
        int input = scanner.nextInt();
        scanner.nextLine();

        for(Sneaker s : sneakerRepository.findSneakerByReleaseYear(input)){
            System.out.println(s);
        }
    }

    private void viewByID(Scanner scanner){
        System.out.println("Sneaker ID: ");
        long input = scanner.nextInt();
        scanner.nextLine();
        Sneaker sneaker = sneakerRepository.findById(input).orElse(null);
        if(sneaker == null){
            System.out.println("No sneakers with that ID");
        } else {
            System.out.println(sneaker.getId() + " - " + sneaker.getModel());
        }
    }
    private void listBrand(Scanner scanner){
        System.out.println("Total = " + brandRepository.count());
        for (Brand b : brandRepository.findAll()) {
            System.out.println(b.getId() + " - " + b.getName());
        }
    }
    private Brand getBrandForShoe(Scanner scanner){
        while(true) {
            System.out.println("Brand: ");
            long input = scanner.nextInt();
            scanner.nextLine();
            Brand brand = brandRepository.findById(input).orElse(null);
            if (brand == null) {
                System.out.println("No brand with that ID");
            } else {
                return brand;
            }
        }
    }

    private void advanceSearch(Scanner scanner){
        System.out.print("Max price: ");
        double price = scanner.nextDouble();
        System.out.print("Min year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        for(Sneaker s : sneakerRepository.search(price, year)){
            System.out.println(s.getId() + " - " + s.getModel());
        }
    }

    private void addShoe(Scanner scanner){
        listBrand(scanner);
        Brand brand = getBrandForShoe(scanner);
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("New Price: ");
        double price = scanner.nextDouble();
        System.out.print("Release year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        sneakerRepository.save(new Sneaker(model, price, year, brand));
        System.out.println("Updated!");
    }

    private void updatePrice(Scanner scanner){
        System.out.print("Snaker ID: ");
        long id = scanner.nextLong();
        Sneaker sneaker = sneakerRepository.findById(id).orElseThrow(() -> new RuntimeException("No Sneakers with id" + id));
        System.out.print("New Price: ");
        sneaker.setPrice(scanner.nextDouble());
        scanner.nextLine();
        sneakerRepository.save(sneaker);
        System.out.println("Updated!");
    }

    private void deleteShoe(Scanner scanner){
        System.out.print("Snaker ID: ");
        long id = scanner.nextLong();
        Sneaker sneaker = sneakerRepository.findById(id).orElseThrow(() -> new RuntimeException("No Sneakers with id" + id));
        scanner.nextLine();
        sneakerRepository.delete(sneaker);
        System.out.println("Updated!");
    }
    private void listShoesByBrand(Scanner scanner){
        listBrand(scanner);
        System.out.print("Brand: ");
        long id = scanner.nextLong();
        for (Sneaker s : sneakerRepository.findSneakerByBrand_Id(id)) {
            System.out.println(s.getId() + " - " + s.getModel());
        }
    }

    private void addBrand(Scanner scanner){
        System.out.print("New Brand: ");
        String brand = scanner.nextLine();
        brandRepository.save(new Brand(brand));
        System.out.println("Updated!");
    }

    private void brandManagement(Scanner scanner){
        while(true) {
            System.out.println("1). Add a Brand");
            System.out.println("2). View all Brands");
            System.out.println("0). Return");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch(input){
                case 1 -> addBrand(scanner);
                case 2 -> listBrand(scanner);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid entry");
            }
        }
    }

    private void seedData() {
        if(brandRepository.count() > 0){
            return;
        }
        Brand Nike = brandRepository.save(new Brand("Nike"));
        Brand Apple = brandRepository.save(new Brand("Apple"));
        Brand Samsung = brandRepository.save(new Brand("Samsung"));

        sneakerRepository.save(new Sneaker("T40", 22.50, 2010, Nike));
        sneakerRepository.save(new Sneaker("Aplle 2.0", 100.99, 2026, Apple));
        sneakerRepository.save(new Sneaker("1.0 bounce", 55.99, 2015, Samsung));
    }
}
