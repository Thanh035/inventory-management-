package com.example.myapp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.myapp.utils.DefaultProfileUtil;

public class ApplicationWebXml extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// set a default to use when no profile is configured.
		DefaultProfileUtil.addDefaultProfile(application.application());
		return application.sources(DemoApplication.class);
	}
}
