package com.app.SystemRestaurant.ArchUnit;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

public class ArchitectureTest {
    @ArchTest
    void servicesShouldNotDependOnControllers(com.tngtech.archunit.core.domain.JavaClasses importedClasses) {
        ArchRuleDefinition.noClasses()
        .that().resideInAPackage("..Service..")
        .should().dependOnClassesThat().resideInAPackage("..Controller..")
        .check(importedClasses);
    }

    @ArchTest
    void controllersShouldOnlyDependOnServices(com.tngtech.archunit.core.domain.JavaClasses importedClasses) {
        ArchRuleDefinition.classes()
        .that().resideInAPackage("..Controller..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage("..Service..", "java..", "org.springframework..")
        .check(importedClasses);
    }
}
