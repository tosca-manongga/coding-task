package com.challenge.coding.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.challenge.coding.github.Application;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
    private MockMvc mvc;

	@Test
	public void positiveTest() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repositoryName}", "tosca-manongga", "coding-task")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		assertEquals("{createdAt=2021-05-19T19:22:18Z, fullName=tosca-manongga/coding-task, description=null, stars=0, cloneUrl=https://github.com/tosca-manongga/coding-task.git}", result);
	}

	@Test
	public void negativeTest() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repositoryName}", "blah", "blah")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		assertEquals("404 NOT_FOUND", result);
	}

}