package com.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


public class Scenario extends AbstractTestNGSpringContextTests {

    @Autowired
    protected Configuration configuration;

}
