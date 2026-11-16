package com.typeb.helloworld.web;

import com.typeb.helloworld.greeting.GreetingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private final GreetingService greetingService;

	public HelloWorldController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GetMapping("/hello-world")
	public ResponseEntity<?> helloWorld(@RequestParam(name = "name", required = false) String name) {
		return greetingService.greet(name)
				.<ResponseEntity<?>>map(message -> ResponseEntity.ok(new MessageResponse(message)))
				.orElseGet(() -> ResponseEntity.badRequest().body(ErrorResponse.invalidInput()));
	}

}
