package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@all"},
        features = {"src/test/features"},
        glue = {"ru.yandex"}
)
public class RunnerTest {
}

