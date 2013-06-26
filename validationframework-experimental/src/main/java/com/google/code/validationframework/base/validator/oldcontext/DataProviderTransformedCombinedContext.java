/*
 * Copyright (c) 2013, Patrick Moawad
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

package com.google.code.validationframework.base.validator.oldcontext;

import com.google.code.validationframework.api.dataprovider.DataProvider;
import com.google.code.validationframework.api.rule.Rule;
import com.google.code.validationframework.api.trigger.Trigger;
import com.google.code.validationframework.base.transform.Transformer;
import com.google.code.validationframework.base.validator.GeneralValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataProviderTransformedCombinedContext<DPO, TDPO> {

    private final Collection<Trigger> triggers;
    private final Collection<DataProvider<DPO>> dataProviders;
    private final Collection<Transformer> dataProvidersOutputTransformers;
    private final GeneralValidator.DataProviderToRuleMapping dataProviderToRuleMapping;

    public DataProviderTransformedCombinedContext(final Collection<Trigger> triggers, //
            final Collection<DataProvider<DPO>> dataProviders, //
            final Collection<Transformer> dataProvidersOutputTransformers, //
            final GeneralValidator.DataProviderToRuleMapping dataProviderToRuleMapping) {
        this.triggers = triggers;
        this.dataProviders = dataProviders;
        this.dataProvidersOutputTransformers = dataProvidersOutputTransformers;
        this.dataProviderToRuleMapping = dataProviderToRuleMapping;
    }

    public <TTDPO> DataProviderTransformedCombinedTransformedContext<DPO,
            TTDPO> transform(final Transformer<Collection<TDPO>, TTDPO> dataProvidersOutputTransformer) {
        final List<Transformer> transformers = new ArrayList<Transformer>();
        if (dataProvidersOutputTransformer != null) {
            transformers.add(dataProvidersOutputTransformer);
        }

        // Change context
        return new DataProviderTransformedCombinedTransformedContext<DPO, TTDPO>(triggers, dataProviders,
                dataProvidersOutputTransformers, dataProviderToRuleMapping, transformers);
    }

    public <RO> RuleContext<DPO, Collection<TDPO>, RO> check(final Rule<Collection<TDPO>, RO> rule) {
        final List<Rule<Collection<TDPO>, RO>> rules = new ArrayList<Rule<Collection<TDPO>, RO>>();
        if (rule != null) {
            rules.add(rule);
        }

        // Change context
        return new RuleContext<DPO, Collection<TDPO>, RO>(triggers, dataProviders, dataProvidersOutputTransformers,
                dataProviderToRuleMapping, null, rules);
    }

    public <RO> RuleContext<DPO, Collection<TDPO>, RO> check(final Collection<Rule<Collection<TDPO>, RO>> rules) {
        final List<Rule<Collection<TDPO>, RO>> ruleList = new ArrayList<Rule<Collection<TDPO>, RO>>();
        if (rules != null) {
            ruleList.addAll(rules);
        }

        // Change context
        return new RuleContext<DPO, Collection<TDPO>, RO>(triggers, dataProviders, dataProvidersOutputTransformers,
                dataProviderToRuleMapping, null, ruleList);
    }
}