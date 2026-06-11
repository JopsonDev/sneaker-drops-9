package com.pluralsight.sneakerdrop.sneakerdrops;

import com.pluralsight.sneakerdrop.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrop.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrop.sneakerdrops.service.SneakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final SneakerService sneakerService;

    @Autowired
    public StartupRunner(SneakerService sneakerService) {
        this.sneakerService = sneakerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
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
        System.out.println("Total = " + sneakerService.countSneakers());
        for (Sneaker s : sneakerService.allSneakers()) {
            System.out.println(s.getId() + " - " + s.getModel());
        }
    }
    private void listSneakersByModel(Scanner scanner){
        System.out.print("Please enter model: ");
        String input = scanner.nextLine();

        for(Sneaker s : sneakerService.findByContains(input)){
            System.out.println(s);
        }
    }
    private void listSneakerByPrice(Scanner scanner){
        System.out.print("Please enter price: ");
        double input = scanner.nextDouble();
        scanner.nextLine();

        for(Sneaker s : sneakerService.findPriceGreaterThan(input)){
            System.out.println(s);
        }
    }

    private void listSneakerByYear(Scanner scanner){
        System.out.print("Please enter release year: ");
        int input = scanner.nextInt();
        scanner.nextLine();

        for(Sneaker s : sneakerService.findByYear(input)){
            System.out.println(s);
        }
    }

    private void viewByID(Scanner scanner){
        System.out.println("Sneaker ID: ");
        long input = scanner.nextInt();
        scanner.nextLine();
        Sneaker sneaker = sneakerService.findById(input);
        if(sneaker == null){
            System.out.println("No sneakers with that ID");
        } else {
            System.out.println(sneaker.getId() + " - " + sneaker.getModel());
        }
    }
    private void listBrand(Scanner scanner){
        System.out.println("Total = " + sneakerService.countBrands());
        for (Brand b : sneakerService.findAllBrands()) {
            System.out.println(b.getId() + " - " + b.getName());
        }
    }
    private Brand getBrandForShoe(Scanner scanner){
        while(true) {
            System.out.println("Brand: ");
            long input = scanner.nextInt();
            scanner.nextLine();
            Brand brand = sneakerService.findBrandById(input);
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

        for(Sneaker s : sneakerService.search(price, year)){
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
        sneakerService.save(model,price,year,brand);
        System.out.println("Updated!");
    }

    private Sneaker checkId(Scanner scanner){
        Sneaker sneaker;
        while (true) {
            System.out.print("Snaker ID: ");
            long id = scanner.nextLong();
            sneaker = sneakerService.findById(id);
            if (sneaker == null){
                System.out.println("Invalid input");
            } else {
                break;
            }
        }
        return sneaker;
    }

    private void updatePrice(Scanner scanner){
        Sneaker sneaker = checkId(scanner);
        System.out.print("New Price: ");
        sneaker.setPrice(scanner.nextDouble());
        scanner.nextLine();
        sneakerService.save(sneaker);
        System.out.println("Updated!");
    }

    private void deleteShoe(Scanner scanner){
        Sneaker sneaker = checkId(scanner);
        sneakerService.delete(sneaker);
        System.out.println("Updated!");
    }
    private void listShoesByBrand(Scanner scanner){
        listBrand(scanner);
        System.out.print("Brand: ");
        long id = scanner.nextLong();
        for (Sneaker s : sneakerService.searchBrand(id)) {
            System.out.println(s.getId() + " - " + s.getModel());
        }
    }

    private void addBrand(Scanner scanner){
        System.out.print("New Brand: ");
        String brand = scanner.nextLine();
        sneakerService.saveBrand(new Brand(brand));
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
        if(sneakerService.countBrands() > 0){
            return;
        }
        Brand Nike = sneakerService.saveBrand(new Brand("Nike"));
        Brand Apple = sneakerService.saveBrand(new Brand("Apple"));
        Brand Samsung = sneakerService.saveBrand(new Brand("Samsung"));

        sneakerService.save(new Sneaker("T40", 22.50, 2010, Nike));
        sneakerService.save(new Sneaker("Aplle 2.0", 100.99, 2026, Apple));
        sneakerService.save(new Sneaker("1.0 bounce", 55.99, 2015, Samsung));
    }
}
