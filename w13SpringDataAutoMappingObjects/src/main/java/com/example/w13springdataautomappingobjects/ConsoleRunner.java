package com.example.w13springdataautomappingobjects;

import com.example.w13springdataautomappingobjects.models.dtos.EmployeeInfoDTO;
import com.example.w13springdataautomappingobjects.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private EmployeeService employeeService;

    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();


//        Address address = new Address("BG", "Veliko Tarnovo", "Poltava");
//        Employee employee = new Employee("First", "Last", BigDecimal.TEN, LocalDate.now(), address);
//
//
//        BasicEmployeeDTO basicEmployeeDTO = modelMapper.map(employee, BasicEmployeeDTO.class);

      List<EmployeeInfoDTO> list =  employeeService.findInfoForBornBefore(LocalDate.of(1990, 1, 1));

    }
}
