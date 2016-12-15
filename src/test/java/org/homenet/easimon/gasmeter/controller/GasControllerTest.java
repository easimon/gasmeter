package org.homenet.easimon.gasmeter.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.Collections;

import org.homenet.easimon.gasmeter.domain.GasRecordRepository;
import org.homenet.easimon.gasmeter.spring.SpringBasedIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@EnableAutoConfiguration
public class GasControllerTest extends SpringBasedIntegrationTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	GasRecordRepository repositoryMock;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		repositoryMock = mock(GasRecordRepository.class);
	}

	@Test
	public void test() throws Exception {
		final ZonedDateTime from = ZonedDateTime.parse("2015-10-10T00:00:00+02:00[Europe/Berlin]");
		final ZonedDateTime to = ZonedDateTime.parse("2015-10-11T00:00:00+02:00[Europe/Berlin]");

		when(repositoryMock.findAllGasRecords()).thenReturn(Collections.emptyList());
		when(repositoryMock.findGasRecordsByPeriod(any(), any())).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/gas").accept(MediaType.ALL))
				 .andExpect(status().isOk())
		 .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
//		 .andExpect(jsonPath("$.datasets", isEmptyOrNullString()))
//		 .andExpect(jsonPath("$.labels", isEmptyOrNullString()));
//		System.out.println(result.getResponse().getContentAsString());
	}
}
