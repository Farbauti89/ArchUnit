/*
 * Copyright 2018 TNG Technology Consulting GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tngtech.archunit.lang;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.core.domain.JavaClasses;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;
import static com.tngtech.archunit.lang.ArchRule.Factory.createBecauseDescription;
import static java.util.Collections.singletonList;

public final class CompositeArchRule implements ArchRule {
    private final List<ArchRule> rules;
    private final String description;

    private CompositeArchRule(List<ArchRule> rules, String description) {
        this.rules = checkNotNull(rules);
        this.description = checkNotNull(description);
    }

    @PublicAPI(usage = ACCESS)
    public static CompositeArchRule of(ArchRule rule) {
        return new CompositeArchRule(singletonList(rule), rule.getDescription());
    }

    @PublicAPI(usage = ACCESS)
    public CompositeArchRule and(ArchRule rule) {
        List<ArchRule> newRules = ImmutableList.<ArchRule>builder().addAll(rules).add(rule).build();
        String newDescription = description + " and " + rule.getDescription();
        return new CompositeArchRule(newRules, newDescription);
    }

    @Override
    public void check(JavaClasses classes) {
        Assertions.check(this, classes);
    }

    @Override
    public CompositeArchRule because(String reason) {
        return new CompositeArchRule(rules, createBecauseDescription(this, reason));
    }

    @Override
    public EvaluationResult evaluate(JavaClasses classes) {
        EvaluationResult result = new EvaluationResult(this, Priority.MEDIUM);
        for (ArchRule rule : rules) {
            result.add(rule.evaluate(classes));
        }
        return result;
    }

    @Override
    public CompositeArchRule as(String newDescription) {
        return new CompositeArchRule(rules, newDescription);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
