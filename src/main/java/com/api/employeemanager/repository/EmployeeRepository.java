package com.api.employeemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.employeemanager.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
