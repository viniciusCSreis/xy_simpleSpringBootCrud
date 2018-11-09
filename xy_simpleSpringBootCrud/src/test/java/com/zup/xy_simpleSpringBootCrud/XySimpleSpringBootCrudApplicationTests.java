package com.zup.xy_simpleSpringBootCrud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class XySimpleSpringBootCrudApplicationTests {

	@Test
	public void contextLoads() {
		assertThat(true,is(true));
	}

}
