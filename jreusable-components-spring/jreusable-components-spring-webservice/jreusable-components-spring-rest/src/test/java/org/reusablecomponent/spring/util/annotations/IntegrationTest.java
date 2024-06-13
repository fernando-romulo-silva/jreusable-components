package org.reusablecomponent.spring.util.annotations;

//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
//import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for integration tests. Marks a test class as an integration test and configures it for Spring Boot testing.
 * Integration tests focus on testing the integration of various components of an application.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 *   &#64;IntegrationTest
 *   class MyIntegrationTest {
 *       // Integration test methods
 *   }
 * }</pre>
 *
 * <p>Integration tests marked with this annotation are loaded in the Spring application context
 * with the profile "it" activated. They are also annotated with Spring's {@code @SpringBootTest}
 * with a random web environment port, {@code @Import} to import additional configurations,
 * and {@code @TestPropertySource} to specify property sources for the test environment.</p>
 *
 * @see org.springframework.boot.test.context.SpringBootTest
 * @see org.springframework.context.annotation.Import
 * @see org.springframework.context.annotation.Profile
 * @see org.springframework.test.context.TestPropertySource
 * @see IntegrationTestConfig
 */

// https://gist.github.com/aoudiamoncef/8c6b474a4c2a93a60bf87b311a1f9de5
@Profile("it")
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @TestPropertySource(locations = "classpath:application-it.yml")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IntegrationTest {

    
}
