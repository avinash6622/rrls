package com.unify.rrls.domain;

public class DocumentCreationBean {
	
	private Integer id;
	private String fileName;
	private String fileContent;
	private Long oppId;
	private String oppName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public Long getOppId() {
		return oppId;
	}
	public void setOppId(Long oppId) {
		this.oppId = oppId;
	}
	public String getOppName() {
		return oppName;
	}
	public void setOppName(String oppName) {
		this.oppName = oppName;
	}

}
