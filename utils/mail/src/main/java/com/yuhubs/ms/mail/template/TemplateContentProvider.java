package com.yuhubs.ms.mail.template;

import java.io.IOException;

public interface TemplateContentProvider {

	String getTemplateContent(String name) throws IOException;

}
