package com.example.w11springdataadvancedquerying;


import com.example.w11springdataadvancedquerying.entities.Shampoo;
import com.example.w11springdataadvancedquerying.services.IngredientService;
import com.example.w11springdataadvancedquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;


    @Autowired
    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

//        for(Shampoo shampoo : this.shampooService.findByBrand("Silk Comb")) {
//            System.out.println(shampoo.getId());
//        }



//      Task 1
//        String size = scanner.nextLine().toUpperCase();
//         for(Shampoo shampoo : this.shampooService.findBySizeOrderById(size)) {
//            System.out.printf("%s %s %.2flv.%n", shampoo.getBrand(), shampoo.getSize(), shampoo.getPrice());
//        }



//      Task 2
//        String size = scanner.nextLine();
//        long labelId = Long.parseLong(scanner.nextLine());
//
//        List<Shampoo> bySizeOrLabelId = this.shampooService.findBySizeOrLabelId(size, labelId);
//
//        bySizeOrLabelId.forEach(s -> System.out.printf("%s %s %.2flv.%n", s.getBrand(), s.getSize(), s.getPrice()));



//      Task 3
//        String price = scanner.nextLine();
//        List<Shampoo> bySizeOrLabelId = this.shampooService.findByPriceGreaterThanOrderByPriceDesc(price);
//        bySizeOrLabelId.forEach(s -> System.out.printf("%s %s %.2flv.%n", s.getBrand(), s.getSize(), s.getPrice()));



//      Task 4
//        String name = scanner.nextLine();
//        List<Ingredient> ingredients = this.ingredientService.findByNameStartsWith(name);
//        ingredients.forEach(i -> System.out.println(i.getName()));



//      Task 5
//        String nextLine = scanner.nextLine();
//        List<String> ingredient = new ArrayList<>();
//        while (!nextLine.isBlank()){
//            ingredient.add(nextLine);
//            nextLine = scanner.nextLine();
//        }
//
//        List<Ingredient> ingredients = this.ingredientService.selectByNames(ingredient);
//        ingredients.forEach(i -> System.out.println(i.getName()));



//     Task 6
//        String price = scanner.nextLine();
//        Long count = this.shampooService.countWithPriceLowerThan(price);
//        System.out.println(count);


//     Task 7
//        String nextLine = scanner.nextLine();
//        List<String> ingredient = new ArrayList<>();
//        while (!nextLine.isBlank()){
//            ingredient.add(nextLine);
//            nextLine = scanner.nextLine();
//        }
//
//        for(Shampoo shampoo : this.shampooService.findByIngredient(ingredient)) {
//            System.out.printf("%s%n", shampoo.getBrand());
//        }


//     Task 8
//        int count = Integer.parseInt(scanner.nextLine());
//                for (Shampoo shampoo : this.shampooService.findWithIngredientCountLessThan(count)) {
//            System.out.println(shampoo.getBrand());
//     }



//     Task 9
//        String name = scanner.nextLine();
//        this.ingredientService.deleteByName(name);



//     Task 10
//        this.ingredientService.updateAllPrice();



//     Task 11
//        String nextLine = scanner.nextLine();
//        List<String> ingredient = new ArrayList<>();
//        while (!nextLine.isBlank()){
//            ingredient.add(nextLine);
//            nextLine = scanner.nextLine();
//        }
//
//        this.ingredientService.updatePriceByName(ingredient);

    }
}
