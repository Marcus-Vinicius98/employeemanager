package com.api.employeemanager.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

public class EmployeeDTO extends RepresentationModel<EmployeeDTO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	@NonNull
	private String name;

	@NotBlank
	@NonNull
	private String email;

	@NotBlank
	@NonNull
	@CPF
	private String cpf;

	@NotBlank
	@NonNull
	private String jobTitle;

	@NotBlank
	@NonNull
	private String phone;

	private String imageUrl;

	@NotBlank
	@NonNull
	@Size(max = 4)
	private String employeeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

}
