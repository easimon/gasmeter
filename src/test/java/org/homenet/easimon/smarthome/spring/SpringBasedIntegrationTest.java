package org.homenet.easimon.smarthome.spring;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class, CommonConfiguration.class })
public abstract class SpringBasedIntegrationTest {
	
}
