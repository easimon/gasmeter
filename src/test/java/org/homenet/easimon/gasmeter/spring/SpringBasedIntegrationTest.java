package org.homenet.easimon.gasmeter.spring;

import org.homenet.easimon.gasmeter.spring.configuration.TestConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestConfiguration.class }, webEnvironment=WebEnvironment.MOCK )
public abstract class SpringBasedIntegrationTest {

}
