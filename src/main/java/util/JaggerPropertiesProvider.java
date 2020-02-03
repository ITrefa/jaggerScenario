package util;

import com.griddynamics.jagger.util.JaggerXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:test.properties")
public class JaggerPropertiesProvider {

    @Autowired
    private ApplicationContext jaggerContext;

    @Autowired
    private Environment testEnv;

    public String getEnvPropertyValue(String key) {
        return ((JaggerXmlApplicationContext) jaggerContext).getEnvironmentProperties().getProperty(key);
    }

    public String getTestPropertyValue(String key) {
        return testEnv.getRequiredProperty(key);
    }
}
