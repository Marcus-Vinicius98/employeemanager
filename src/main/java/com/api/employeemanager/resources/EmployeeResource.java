package com.api.employeemanager.resources;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1/employee")
@Tag(name = "Employee", description = "Endpoints for Managing Employees")
public class EmployeeResource {

	@Autowired
	private EmployeeService service;

	@Autowired
	private ModelMapper mapper;

	@PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Create a Employee", description = "Create a Employee", tags = { "Employee" }, responses = {
			@ApiResponse(description = "success", responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EmployeeDTO.class))

			}), @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Erroe", responseCode = "500", content = @Content) })

	public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO obj) {
		var entity = service.save(obj);

		var dto = mapper.map(entity, EmployeeDTO.class);

		dto.add(linkTo(methodOn(EmployeeResource.class).findById(dto.getId())).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@GetMapping(value = "/find/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds a Employee", description = "Finds a Employee", tags = { "Employee" }, responses = {
			@ApiResponse(description = "success", responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EmployeeDTO.class))

			}), @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Erroe", responseCode = "500", content = @Content) })
	public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
		var obj = service.findById(id);

		var dto = mapper.map(obj, EmployeeDTO.class);

		dto.add(linkTo(methodOn(EmployeeResource.class).findById(id)).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@GetMapping(value = "/all",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Finds All Employees", description = "Finds All Employess", tags = {
			"Employee" }, responses = { @ApiResponse(description = "success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EmployeeDTO.class))) }),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Erroe", responseCode = "500", content = @Content) })

	public ResponseEntity<CollectionModel<EmployeeDTO>> findAll() {

		var obj = CollectionModel
				.of(service.findAll().stream().map(x -> mapper.map(x, EmployeeDTO.class)).collect(Collectors.toList()));

		obj.forEach(p -> p.add(linkTo(methodOn(EmployeeResource.class).findById(p.getId())).withSelfRel()));
		obj.add(linkTo(methodOn(EmployeeResource.class).findAll()).withSelfRel());

		return ResponseEntity.ok().body(obj);

	}

	@PutMapping(value = "/update/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Update a Employee", description = "Update a Employee", tags = { "Employee" }, responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = {
					@Content(schema = @Schema(implementation = EmployeeDTO.class))

			}), @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Erroe", responseCode = "500", content = @Content) })
	public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO obj) {
		obj.setId(id);
		var dto = mapper.map(service.update(obj), EmployeeDTO.class);
		dto.add(linkTo(methodOn(EmployeeResource.class).findById(id)).withSelfRel());

		return ResponseEntity.ok().body(dto);

	}

	@DeleteMapping(value = "/delete/{id}")
	@Operation(summary = "Delete a Employee", description = "Delete a Employee", tags = { "Employee" }, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = { @Content

			}), @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Erroe", responseCode = "500", content = @Content) })
	public ResponseEntity<EmployeeDTO> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
