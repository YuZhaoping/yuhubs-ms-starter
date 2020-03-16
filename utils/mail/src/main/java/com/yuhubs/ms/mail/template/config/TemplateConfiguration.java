package com.yuhubs.ms.mail.template.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class TemplateConfiguration {

	protected List<TemplateEntry> entries = new ArrayList<>();

	protected Map<String, String> variables = new HashMap<>();

	protected String location;


	public List<TemplateEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<TemplateEntry> entries) {
		this.entries = entries;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
