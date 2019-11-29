package ru.yandex;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@fourth"},
        format = {"pretty", "json:target/cucumber.json","html:target/cucumber.html"},
        features = {"src/test/features"},
        glue = {"ru.yandex"}
)
public class RunnerTest {
}