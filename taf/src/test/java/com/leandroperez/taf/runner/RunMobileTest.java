package com.leandroperez.taf.runner;

import org.junit.platform.suite.api.*;
import io.cucumber.junit.platform.engine.Constants;

@Suite
@IncludeEngines("cucumber")
//@SelectClasspathResource("com.leandroperez.taf/src/test/resources/features/mobile")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME,value = "src/test/resources/features/mobile/firefoxapp")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME,value = "com/leandroperez/taf/sut/mobile/firefoxapp/pom/step")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value = "@SISTEMA and @TAG2")
@ConfigurationParameter(key = Constants.EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME,value = "pretty, html:target/cucumber-report/cucumber.html")
//@IncludeTags({"SISTEMA", "TAG1"})
public class RunMobileTest {
}
