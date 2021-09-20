package com.obamax.ajo;

import com.obamax.ajo.seeder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AjoApplication {

	@Autowired
	private SeedUser seedUser;

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(AjoApplication.class, args);
		AjoApplication app = run.getBean(AjoApplication.class);
		app.runSeed();
	}

	@PostConstruct
	private void runSeed() {
		seedUser.loadUserData();
	}
}
