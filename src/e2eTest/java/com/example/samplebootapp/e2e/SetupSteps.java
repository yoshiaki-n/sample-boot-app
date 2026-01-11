package com.example.samplebootapp.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.Given;

public class SetupSteps {
  @Given("the E2E test environment is configured")
  public void the_e2e_test_environment_is_configured() {
    assertTrue(true);
  }
}
