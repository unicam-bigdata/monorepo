package com.bigdata.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot! Hello yow.. i am changing the code";
	}

}
