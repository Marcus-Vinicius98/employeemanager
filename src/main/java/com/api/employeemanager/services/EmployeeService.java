package com.api.employeemanager.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.employeemanager.domain.Employee;
import com.api.employeemanager.dto.EmployeeDTO;
import com.api.employeemanager.repository.EmployeeRepository;
import com.api.employeemanager.services.exceptions.DataIntegrityViolationException;
import com.api.employeemanager.services.exceptions.ObjectNotFoundException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private ModelMapper mapper;

	public Employee save(EmployeeDTO obj) {
		findByCpf(obj);

		return repository.save(mapper.map(obj, Employee.class));
	}

	public Employee findById(Long id) {
		var obj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		return obj;
	}

	public List<Employee> findAll() {
		return repository.findAll();
	}

	public Employee update(EmployeeDTO obj) {
		findByCpf(obj);
		return repository.save(mapper.map(obj, Employee.class));
	}

	public void delete(Long id) {
		var obj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		repository.delete(obj);
	}

	public void findByCpf(EmployeeDTO obj) {
		Optional<Employee> user = repository.findByCpf(obj.getCpf());
		if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegrityViolationException("Objeto já cadastrado");
		}
	}
}
