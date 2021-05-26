package com.challenge.coding.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.challenge.coding.github.Application;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	public void positiveTest1() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/{owner}/{repositoryName}", "tosca-manongga", "coding-task")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT, new Customization("stars", (o1, o2) -> (o1 instanceof Number && o2 instanceof Number)));

		JSONAssert.assertEquals("{\"fullName\":\"tosca-manongga/coding-task\",\"description\":null,\"cloneUrl\":\"https://github.com/tosca-manongga/coding-task.git\",\"stars\":0,\"createdAt\":\"2021-05-19T19:22:18Z\"}", result, comparator);
	}

	@Test
	public void positiveTest2() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/{owner}/{repositoryName}", "iptv-org", "iptv")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT, new Customization("stars", (o1, o2) -> (o1 instanceof Number && o2 instanceof Number)));

		JSONAssert.assertEquals("{\"fullName\":\"iptv-org/iptv\",\"description\":\"Collection of publicly available IPTV channels from all over the world\",\"cloneUrl\":\"https://github.com/iptv-org/iptv.git\",\"stars\":33844,\"createdAt\":\"2018-11-14T22:00:57Z\"}", result, comparator);
	}

	@Test
	public void positiveTest3() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/{owner}/{repositoryName}", "spring-projects", "spring-boot")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT, new Customization("stars", (o1, o2) -> (o1 instanceof Number && o2 instanceof Number)));

		JSONAssert.assertEquals("{\"fullName\":\"spring-projects/spring-boot\",\"description\":\"Spring Boot\",\"cloneUrl\":\"https://github.com/spring-projects/spring-boot.git\",\"stars\":55282,\"createdAt\":\"2012-10-19T15:02:57Z\"}", result, comparator);
	}

	@Test
	public void positiveTest4() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/{owner}/{repositoryName}", "elastic", "elasticsearch")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		CustomComparator comparator = new CustomComparator(JSONCompareMode.STRICT, new Customization("stars", (o1, o2) -> (o1 instanceof Number && o2 instanceof Number)));

		JSONAssert.assertEquals("{\"fullName\":\"elastic/elasticsearch\",\"description\":\"Free and Open, Distributed, RESTful Search Engine\",\"cloneUrl\":\"https://github.com/elastic/elasticsearch.git\",\"stars\":55064,\"createdAt\":\"2010-02-08T13:20:56Z\"}", result, comparator);
	}

	@Test
	public void negativeTest1() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/blah/blah")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		assertEquals("", result);
	}

	@Test
	public void negativeTest2() throws Exception {
		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders
						.get("/repositories/blah/blah/blah")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		assertEquals("", result);
	}
}