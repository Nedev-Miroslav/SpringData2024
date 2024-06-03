package com.example.w13springdataautomappingobjects.service;

import com.example.w13springdataautomappingobjects.models.dtos.EmployeeInfoDTO;

import java.time.LocalDate;
import java.util.List;


public interface EmployeeService {

    List<EmployeeInfoDTO> findInfoForBornBefore(LocalDate before);
}
