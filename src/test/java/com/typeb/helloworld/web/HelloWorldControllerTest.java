package com.typeb.helloworld.web;

import com.typeb.helloworld.greeting.GreetingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloWorldController.class)
@Import(GreetingService.class)
class HelloWorldControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void returnsGreetingWhenNameStartsInTheFirstHalf() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "alice"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Hello Alice"))
				.andExpect(jsonPath("$.error").doesNotExist());
	}

	@Test
	void acceptsTheLastLetterOfTheFirstHalf() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "Mike"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Hello Mike"));
	}

	@Test
	void trimsSurroundingWhitespaceBeforeClassifyingTheName() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "  bob  "))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Hello Bob"));
	}

	@Test
	void rejectsANameInTheSecondHalf() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "nancy"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.error").value("Invalid Input"))
				.andExpect(jsonPath("$.message").doesNotExist());
	}

	@Test
	void rejectsTheFirstLetterOfTheSecondHalf() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "N"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	void rejectsAMissingName() throws Exception {
		mockMvc.perform(get("/hello-world"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	void rejectsAnEmptyName() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", ""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	void rejectsABlankName() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "   "))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	void rejectsANameThatDoesNotStartWithALetter() throws Exception {
		mockMvc.perform(get("/hello-world").param("name", "1alice"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Invalid Input"));
	}

	@Test
	void rejectsOtherHttpMethods() throws Exception {
		mockMvc.perform(post("/hello-world").param("name", "alice"))
				.andExpect(status().isMethodNotAllowed());
	}

}
