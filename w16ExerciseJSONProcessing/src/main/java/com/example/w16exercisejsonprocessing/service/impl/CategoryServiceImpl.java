package com.example.w16exercisejsonprocessing.service.impl;

import com.example.w16exercisejsonprocessing.data.entities.Category;
import com.example.w16exercisejsonprocessing.data.entities.Product;
import com.example.w16exercisejsonprocessing.data.repositories.CategoryRepository;
import com.example.w16exercisejsonprocessing.service.dtos.imports.CategoryByProductsDto;
import com.example.w16exercisejsonprocessing.service.dtos.imports.CategorySeedDto;
import com.example.w16exercisejsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    // CONSTANTS
    private static final String FILE_PATH = "src\\main\\resources\\json\\categories.json";


    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));

          //  Друг начин за прочитане на информацията от файла
          //  this.gson.fromJson(new FileReader(FILE_PATH), CategorySeedDto[].class);

            CategorySeedDto[] categorySeedDtos = this.gson.fromJson(jsonContent, CategorySeedDto[].class);

            for (CategorySeedDto categorySeedDto : categorySeedDtos) {
                if (!this.validationUtil.isValid(categorySeedDto)) {
                    this.validationUtil.getViolations(categorySeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                Category category = this.modelMapper.map(categorySeedDto, Category.class);
                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public List<CategoryByProductsDto> getAllCategoriesByProducts() {
        return this.categoryRepository.findAllCategoriesByProducts()
                .stream()
                .map(c -> {
                    CategoryByProductsDto dto = this.modelMapper.map(c, CategoryByProductsDto.class);
                    dto.setProductsCount(c.getProducts().size());
                    BigDecimal sum = c.getProducts().stream().map(Product::getPrice).reduce(BigDecimal::add).get();
                    dto.setTotalRevenue(sum);
                    dto.setAveragePrice(sum.divide(BigDecimal.valueOf(c.getProducts().size()), MathContext.DECIMAL64));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void printAllCategoriesByProducts() {
        String json = this.gson.toJson(this.getAllCategoriesByProducts());
        System.out.println(json);
    }
}