package com.example.fitness;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.example")
public class OnionArchitectureTest {

    @ArchTest
    static final ArchRule onion_architecture_is_respected = onionArchitecture()
            .domainModels("..domain..")
            .domainServices("..domain.service..")
            .applicationServices("..application..")
            .adapter("infrastructure", "..infrastructure..")
            .adapter("presentation", "..presentation..");
}
