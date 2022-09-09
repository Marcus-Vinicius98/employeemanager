package com.api.employeemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.employeemanager.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
	Optional<Employee> findByCpf(String cpf);

}
