package com.example.w18exercisexmlprocessing.service.impl;

import com.example.w18exercisexmlprocessing.data.entities.Car;
import com.example.w18exercisexmlprocessing.data.entities.Customer;
import com.example.w18exercisexmlprocessing.data.entities.Part;
import com.example.w18exercisexmlprocessing.data.entities.Sale;
import com.example.w18exercisexmlprocessing.data.repositories.CarRepository;
import com.example.w18exercisexmlprocessing.data.repositories.CustomerRepository;
import com.example.w18exercisexmlprocessing.data.repositories.SaleRepository;
import com.example.w18exercisexmlprocessing.service.SaleService;
import com.example.w18exercisexmlprocessing.service.dto.exports.CarDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.SaleDiscountDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.SaleDiscountsRootDto;
import com.example.w18exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final List<Double> discount = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;



    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper mapper, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;

        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSales() {
        if(this.saleRepository.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(gerRandomDiscount());
                this.saleRepository.saveAndFlush(sale);

            }

        }
    }

    @Override
    public void exportSales() throws JAXBException {
        List<SaleDiscountDto> saleDiscountDtos = this.saleRepository
                .findAll()
                .stream()
                .map(s -> {
                    SaleDiscountDto saleDiscountDto = this.mapper.map(s, SaleDiscountDto.class);
                    CarDto car = this.mapper.map(s.getCar(), CarDto.class);

                    saleDiscountDto.setCarDto(car);
                    saleDiscountDto.setCustomerName(s.getCustomer().getName());
                    saleDiscountDto.setPrice(s.getCar().getParts().stream().map(Part::getPrice).reduce(BigDecimal::add).get());
                    saleDiscountDto.setPriceWithDiscount(saleDiscountDto.getPrice().multiply(BigDecimal.valueOf(s.getDiscount())));
                    return saleDiscountDto;
                })
                .collect(Collectors.toList());

        SaleDiscountsRootDto saleDiscountsRootDto = new SaleDiscountsRootDto();
        saleDiscountsRootDto.setSaleDiscountDtos(saleDiscountDtos);

        this.xmlParser.exportToFile(SaleDiscountsRootDto.class, saleDiscountsRootDto, "src\\main\\resources\\xml\\exports\\sale.xml");
    }

    private double gerRandomDiscount() {
        return discount.get(ThreadLocalRandom.current().nextInt(0,  discount.size()));
    }

    private Customer getRandomCustomer() {
        return this.customerRepository.findById(ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1)).get();
    }

    private Car getRandomCar() {
        return this.carRepository.findById(ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1)).get();

    }
}
