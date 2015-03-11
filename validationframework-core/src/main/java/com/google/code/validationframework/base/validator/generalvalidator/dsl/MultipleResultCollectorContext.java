/*
 * Copyright (c) 2015, ValidationFramework Authors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.code.validationframework.base.validator.generalvalidator.dsl;

import com.google.code.validationframework.api.dataprovider.DataProvider;
import com.google.code.validationframework.api.property.ReadableProperty;
import com.google.code.validationframework.api.resulthandler.ResultHandler;
import com.google.code.validationframework.api.rule.Rule;
import com.google.code.validationframework.api.transform.Transformer;
import com.google.code.validationframework.api.trigger.Trigger;
import com.google.code.validationframework.api.validator.SimpleValidator;
import com.google.code.validationframework.base.dataprovider.PropertyValueProvider;
import com.google.code.validationframework.base.resulthandler.ResultCollector;
import com.google.code.validationframework.base.resulthandler.SimpleResultCollector;
import com.google.code.validationframework.base.trigger.PropertyValueChangeTrigger;
import com.google.code.validationframework.base.validator.generalvalidator.GeneralValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DSL-related context of the {@link GeneralValidatorBuilder} after adding more result collectors.
 *
 * @param <DPO> Type of data provider output.
 */
public class MultipleResultCollectorContext<DPO> {

    /**
     * Triggers to be added to the validator under construction.
     */
    private final Collection<Trigger> addedTriggers;

    /**
     * Data providers to be added to the validator under construction.
     */
    private final Collection<DataProvider<DPO>> addedDataProviders;

    /**
     * Constructor specifying the already known elements of the validator under construction.
     *
     * @param addedTriggers      Triggers to be added.
     * @param addedDataProviders Data providers to be added.
     */
    public MultipleResultCollectorContext(Collection<Trigger> addedTriggers, //
                                          Collection<DataProvider<DPO>> addedDataProviders) {
        this.addedTriggers = addedTriggers;
        this.addedDataProviders = addedDataProviders;
    }

    /**
     * Adds the specified result collector as trigger and data provider to the validator under construction.
     *
     * @param resultCollector Result collector to be added.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public MultipleResultCollectorContext<DPO> collect(ResultCollector<?, DPO> resultCollector) {
        if (resultCollector != null) {
            addedTriggers.add(resultCollector);
            addedDataProviders.add(resultCollector);
        }

        // Stay in the same context and re-use the same instance because no type has changed
        return this;
    }

    public MultipleResultCollectorContext<DPO> collect(ReadableProperty<DPO> property) {
        if (property != null) {
            addedTriggers.add(new PropertyValueChangeTrigger(property));
            addedDataProviders.add(new PropertyValueProvider<DPO>(property));
        }

        // Stay in the same context and re-use the same instance because no type has changed
        return this;
    }

    /**
     * Adds the specified result collectors as triggers and data providers to the validator under construction.
     *
     * @param resultCollectors Result collectors to be added.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public MultipleResultCollectorContext<DPO> collect(Collection<ResultCollector<?, DPO>> resultCollectors) {
        if (resultCollectors != null) {
            addedTriggers.addAll(resultCollectors);
            addedDataProviders.addAll(resultCollectors);
        }

        // Stay in the same context and re-use the same instance because no type has changed
        return this;
    }

    /**
     * Adds a new result collector as trigger and data provider to the validator under construction, in order to collect
     * the results of the specified validator.
     *
     * @param validator Validator to collect the result from.<br>
     *                  A result collector will be created, added as a result handler to the specified validator, and
     *                  added as a trigger and data provider in the validator under construction.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public MultipleResultCollectorContext<DPO> collect(SimpleValidator<?, ?, ?, ?, ?, ?, ResultHandler<DPO>,
            DPO> validator) {
        if (validator != null) {
            // Create result collector
            SimpleResultCollector<DPO> resultCollector = new SimpleResultCollector<DPO>();

            // Register result collector in specified validator
            validator.addResultHandler(resultCollector);

            // Result result collector in validator under construction
            addedTriggers.add(resultCollector);
            addedDataProviders.add(resultCollector);
        }

        // Stay in the same context and re-use the same instance because no type has changed
        return this;
    }

    /**
     * Makes the validator process each data provider independently.
     * <p/>
     * This corresponds to the use of {@link GeneralValidator.MappingStrategy#SPLIT}.
     *
     * @return Context allowing further construction of the validator using the DSL.
     *
     * @see GeneralValidator.MappingStrategy#SPLIT
     */
    public ForEachDataProviderContext<DPO> forEach() {
        // Change context
        return new ForEachDataProviderContext<DPO>(addedTriggers, addedDataProviders);
    }

    /**
     * Adds the specified rule input transformer to the validator under construction.
     *
     * @param ruleInputTransformer Rule input transformer to be added.
     * @param <TDPO>               Type of transformer output.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public <TDPO> TransformedDataProviderContext transform(Transformer<Collection<DPO>, TDPO> ruleInputTransformer) {
        List<Transformer> transformers = new ArrayList<Transformer>();
        if (ruleInputTransformer != null) {
            transformers.add(ruleInputTransformer);
        }

        return new TransformedDataProviderContext<DPO, TDPO>(addedTriggers, addedDataProviders,
                GeneralValidator.MappingStrategy.JOIN, transformers);
    }

    /**
     * Adds the specified rule to the validator under construction.
     *
     * @param rule Rule to be added.
     * @param <RO> Type of rule output.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public <RO> SingleRuleContext<DPO, Collection<DPO>, RO> check(Rule<Collection<DPO>, RO> rule) {
        List<Rule<Collection<DPO>, RO>> addedRules = new ArrayList<Rule<Collection<DPO>, RO>>();
        if (rule != null) {
            addedRules.add(rule);
        }

        // Change context
        return new SingleRuleContext<DPO, Collection<DPO>, RO>(addedTriggers, addedDataProviders,
                GeneralValidator.MappingStrategy.JOIN, null, addedRules);
    }

    /**
     * Adds the specified rules to the validator under construction.
     *
     * @param rules Rules to be added.
     * @param <RO>  Type of rules output.
     *
     * @return Context allowing further construction of the validator using the DSL.
     */
    public <RO> SingleRuleContext<DPO, Collection<DPO>, RO> check(Collection<Rule<Collection<DPO>, RO>> rules) {
        List<Rule<Collection<DPO>, RO>> addedRules = new ArrayList<Rule<Collection<DPO>, RO>>();
        if (rules != null) {
            addedRules.addAll(rules);
        }

        // Change context
        return new SingleRuleContext<DPO, Collection<DPO>, RO>(addedTriggers, addedDataProviders,
                GeneralValidator.MappingStrategy.JOIN, null, addedRules);
    }
}
