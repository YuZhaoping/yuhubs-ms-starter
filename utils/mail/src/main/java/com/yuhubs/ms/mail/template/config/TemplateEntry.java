package com.yuhubs.ms.mail.template.config;

public final class TemplateEntry {

	private String id;

	private String subject;

	private String contentType;

	private volatile int contentLength;

	private String location;

	private String fileName;

	private String from;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String type) {
		this.contentType = type;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int length) {
		this.contentLength = length;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
