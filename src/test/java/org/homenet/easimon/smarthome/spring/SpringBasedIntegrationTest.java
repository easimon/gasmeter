package org.homenet.easimon.smarthome.spring;

import org.homenet.easimon.smarthome.spring.configuration.TestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public abstract class SpringBasedIntegrationTest {

}
