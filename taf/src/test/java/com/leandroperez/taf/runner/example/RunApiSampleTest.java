package com.leandroperez.taf.runner.example;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * @author Leandro Henrique Perez
 */

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com/leandroperez/taf/sut/api/petstore/sop/step")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@SISTEMA and @API")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME, value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report/cucumber.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunApiSampleTest {
}
