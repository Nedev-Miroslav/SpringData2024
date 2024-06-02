package com.example.w13springdataautomappingobjects.repositories;

import com.example.w13springdataautomappingobjects.models.Employee;
import com.example.w13springdataautomappingobjects.models.dtos.EmployeeInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT new com.example.w13springdataautomappingobjects.models.dtos.EmployeeInfoDTO(e.firstName, e.lastName, e.salary, e.birthDay) FROM Employee e WHERE e.birthDay < :before")
    List<EmployeeInfoDTO> findAllByBirthdayBefore(LocalDate before);
}
