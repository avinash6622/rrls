package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "external_research")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalResearchAnalyst  extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ext_name")
	private String name;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "contact_no")
	private String contactNo;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "description")
	private String description;
	
	@Transient
	@JsonProperty
	private List<OpportunitySector> opportunitySector;
	
	@Transient
	@JsonProperty
	private List<ExternalRAContacts> externalRAContacts;
	
	@OneToMany(mappedBy = "externalResearchAnalyst")
	private List<ExternalRAFileUpload> fileUploads;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OpportunitySector> getOpportunitySector() {
		return opportunitySector;
	}

	public void setOpportunitySector(List<OpportunitySector> opportunitySector) {
		this.opportunitySector = opportunitySector;
	}

	public List<ExternalRAContacts> getExternalRAContacts() {
		return externalRAContacts;
	}

	public void setExternalRAContacts(List<ExternalRAContacts> externalRAContacts) {
		this.externalRAContacts = externalRAContacts;
	}
	public List<ExternalRAFileUpload> getFileUploads() {
		return fileUploads;
	}
	public void setFileUploads(List<ExternalRAFileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}
	
}
