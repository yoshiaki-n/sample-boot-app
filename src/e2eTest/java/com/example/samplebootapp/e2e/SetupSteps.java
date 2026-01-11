package com.example.samplebootapp.e2e;

import io.cucumber.java.en.Given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetupSteps {
    @Given("the E2E test environment is configured")
    public void the_e2e_test_environment_is_configured() {
        assertTrue(true);
    }
}
