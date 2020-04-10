package com.yuhubs.ms.auth.app;

import com.yuhubs.ms.mail.MailConfigurationSupport;
import com.yuhubs.ms.mail.MailTemplateManager;
import com.yuhubs.ms.mail.MailTemplateSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;

@Configuration
public class MailConfig extends MailConfigurationSupport {

	@Component
	public static class PreloadTemplatesTask implements CommandLineRunner {

		@Autowired
		private MailTemplateManager templateManager;

		@Autowired
		private MailTemplateSupport templateSupport;


		@Override
		public void run(String... args) throws Exception {
			templateManager.preloadTemplates(templateSupport);
		}

	}


	private final File homeDir = new ApplicationHome(Application.class).getDir();


	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private MailTemplateManager mailTemplateManager;


	@Override
	protected ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	@Override
	protected MailTemplateManager getMailTemplateManager() {
		return this.mailTemplateManager;
	}

	@Override
	protected String getRealPath(String path) {
		File file = new File(homeDir, path);
		file = file.toPath().normalize().toFile();
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		return super.getRealPath(path);
	}

}
