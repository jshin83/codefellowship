package com.thefuzzydragon.jen.codefellowship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CodefellowshipApplicationTests {
	@Autowired
	AppUserController controller;


	@Autowired
	MockMvc mockMvc;


	@Test
	public void contextLoads() {
	}



	@Test
	public void testControllerIsAutowired() {
		assertNotNull(controller);
	}


	@Test
	public void testHomeRoute() throws Exception{
		mockMvc.perform(get("/")).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void testsignupRoute() throws Exception{
		mockMvc.perform(get("/signup")).andDo(print())
				.andExpect(status().isOk());

	}



}
