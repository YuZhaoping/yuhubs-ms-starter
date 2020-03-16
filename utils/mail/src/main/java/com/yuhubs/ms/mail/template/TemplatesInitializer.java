package com.yuhubs.ms.mail.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

public final class TemplatesInitializer {

	private final Configuration freemarker;

	private final StringTemplateLoader loader;


	TemplatesInitializer(Configuration freemarker) {
		this.freemarker = freemarker;

		this.loader = (StringTemplateLoader)freemarker.getTemplateLoader();
	}


	public TemplatesInitializer begin() {
		return this;
	}

	public TemplatesInitializer putTemplate(String name, String content) {
		this.loader.putTemplate(name, content);
		return this;
	}

	public void end() {
		this.freemarker.setTemplateLoader(this.loader);

		this.freemarker.clearTemplateCache();
	}

}
