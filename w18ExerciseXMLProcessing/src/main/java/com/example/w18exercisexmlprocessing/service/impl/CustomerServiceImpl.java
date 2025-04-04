package com.example.w18exercisexmlprocessing.service.impl;

import com.example.w18exercisexmlprocessing.data.entities.Customer;
import com.example.w18exercisexmlprocessing.data.repositories.CustomerRepository;
import com.example.w18exercisexmlprocessing.service.CustomerService;
import com.example.w18exercisexmlprocessing.service.dto.exports.CustomerOrderedDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.CustomerOrderedRootDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.CustomerTotalSalesDto;
import com.example.w18exercisexmlprocessing.service.dto.exports.CustomerTotalSalesRootDto;
import com.example.w18exercisexmlprocessing.service.dto.imports.CustomerSeedRootDto;
import com.example.w18exercisexmlprocessing.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String FILE_IMPORT_PATH = "src\\main\\resources\\xml\\imports\\customers.xml";
    private static final String FILE_EXPORT_CUSTOMER_PATH = "src\\main\\resources\\xml\\exports\\ordered-customer.xml";
    private static final String FILE_EXPORT_CUSTOMERS_BOUGHT_PATH = "src\\main\\resources\\xml\\exports\\customer-with-cars.xml";


    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;

        this.mapper = mapper;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        if(this.customerRepository.count() == 0) {
            CustomerSeedRootDto customerSeedRootDto = this.xmlParser.parse(CustomerSeedRootDto.class, FILE_IMPORT_PATH);
            customerSeedRootDto.getCustomerSeedDto().forEach(c ->
                    this.customerRepository.saveAndFlush(this.mapper.map(c, Customer.class)));

        }

    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedDto> customerOrderedDtos = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream()
                .map(c -> this.mapper.map(c, CustomerOrderedDto.class))
                .collect(Collectors.toList());


        CustomerOrderedRootDto customerOrderedRootDto = new CustomerOrderedRootDto();
        customerOrderedRootDto.setCustomerOrderedDto(customerOrderedDtos);
          this.xmlParser.exportToFile(CustomerOrderedRootDto.class, customerOrderedRootDto, FILE_EXPORT_CUSTOMER_PATH);
    }

    @Override
    public void exportCustomersWithBoughtCars() throws JAXBException {
        List<CustomerTotalSalesDto> collect = this.customerRepository.findAllWithBoughtCars()
                .stream()
                .map(c -> {
                    CustomerTotalSalesDto customerTotalSalesDto = new CustomerTotalSalesDto();
                    customerTotalSalesDto.setFullName(c.getName());
                    customerTotalSalesDto.setBoughtCars(c.getSales().size());
                    double spentMoney = c.getSales()
                            .stream()
                            .mapToDouble(s -> s.getCar().getParts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum() * s.getDiscount())
                            .sum();
                    customerTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));

                    return customerTotalSalesDto;
                })
                .sorted(Comparator.comparing(CustomerTotalSalesDto::getBoughtCars).reversed()
                        .thenComparing(Comparator.comparing(CustomerTotalSalesDto::getSpentMoney).reversed()))
                .collect(Collectors.toList());

        CustomerTotalSalesRootDto customerTotalSalesRootDto = new CustomerTotalSalesRootDto();
        customerTotalSalesRootDto.setCustomerTotalSalesDtos(collect);

        this.xmlParser.exportToFile(CustomerTotalSalesRootDto.class, customerTotalSalesRootDto, FILE_EXPORT_CUSTOMERS_BOUGHT_PATH);
    }
}