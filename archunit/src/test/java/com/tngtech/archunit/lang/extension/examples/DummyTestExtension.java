package com.tngtech.archunit.lang.extension.examples;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.extension.ArchUnitExtension;
import com.tngtech.archunit.lang.extension.EvaluatedRule;

import java.util.Properties;

public class DummyTestExtension implements ArchUnitExtension {
    public static final String UNIQUE_IDENTIFIER = DummyTestExtension.class.getName().replace(".", "_");

    @Override
    public String getUniqueIdentifier() {
        return UNIQUE_IDENTIFIER;
    }

    @Override
    public void configure(Properties properties) {
    }

    @Override
    public void handle(EvaluatedRule evaluatedRule) {
    }

    @Override
    public void onFinishAnalyzingClasses(JavaClasses classes) {
    }
}
