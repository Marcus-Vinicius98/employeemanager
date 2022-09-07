package com.api.employeemanager.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.employeemanager.dto.EmployeeDTO;
import com.api.employeemanager.services.EmployeeService;

@RestController
@RequestMapping(value = "employee")
public class EmployeeResource {

	@Autowired
	private EmployeeService service;

	@Autowired
	private ModelMapper mapper;

	@PostMapping(value = "/add")
	public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO obj) {
		var entity = service.save(obj);

		var dto = mapper.map(entity, EmployeeDTO.class);

		dto.add(linkTo(methodOn(EmployeeResource.class).findById(dto.getId())).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
		var obj = service.findById(id);

		var dto = mapper.map(obj, EmployeeDTO.class);

		dto.add(linkTo(methodOn(EmployeeResource.class).findById(id)).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@GetMapping(value = "/all")
	public ResponseEntity<CollectionModel<EmployeeDTO>> findAll() {

		var obj = CollectionModel
				.of(service.findAll().stream().map(x -> mapper.map(x, EmployeeDTO.class)).collect(Collectors.toList()));

		obj.forEach(p -> p.add(linkTo(methodOn(EmployeeResource.class).findById(p.getId())).withSelfRel()));
		obj.add(linkTo(methodOn(EmployeeResource.class).findAll()).withSelfRel());

		return ResponseEntity.ok().body(obj);

	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO obj) {
		obj.setId(id);
		var dto = mapper.map(service.update(obj), EmployeeDTO.class);
		dto.add(linkTo(methodOn(EmployeeResource.class).findById(id)).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<EmployeeDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
