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

package com.google.code.validationframework.base.validator;

import com.google.code.validationframework.api.dataprovider.DataProvider;
import com.google.code.validationframework.api.resulthandler.ResultHandler;
import com.google.code.validationframework.api.rule.Rule;
import com.google.code.validationframework.api.transform.Aggregator;
import com.google.code.validationframework.api.trigger.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Concrete implementation of a simple validator that aggregates the results of all the rules into a single result
 * before passing it to the results handlers.
 * <p>
 * This can be useful, for instance, to combine boolean results using the AND or OR operations.
 * <p>
 * Use {@link com.google.code.validationframework.base.validator.generalvalidator.GeneralValidator} or
 * {@link com.google.code.validationframework.base.validator.generalvalidator.dsl.GeneralValidatorBuilder} instead.
 *
 * @param <RI>  Type of data to be validated.<br>
 *              It can be, for instance, the type of data handled by a component, or the type of the component itself.
 * @param <RO>  Type of validation result produced by the rules.<br>
 *              It can be, for instance, an enumeration or just a boolean.
 * @param <RHI> Type of aggregated result.
 *
 * @see AbstractSimpleValidator
 * @see Trigger
 * @see DataProvider
 * @see Rule
 * @see ResultHandler
 */
public class ResultAggregationValidator<RI, RO, RHI> extends AbstractSimpleValidator<Trigger, DataProvider<RI>, RI,
        Rule<RI, RO>, RI, RO, ResultHandler<RHI>, RHI> {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultAggregationValidator.class);

    /**
     * Transformer aggregating the rule results into a single result to be passed to the result handlers.
     */
    private Aggregator<RO, RHI> resultAggregator = null;

    /**
     * Constructor specifying the transformer aggregating the rule results into a single result to be passed to the
     * result handlers.
     *
     * @param resultAggregator Transformer aggregating the rule results into a single result.
     */
    public ResultAggregationValidator(Aggregator<RO, RHI> resultAggregator) {
        this.resultAggregator = resultAggregator;
    }

    /**
     * @see AbstractSimpleValidator#processTrigger(Trigger)
     */
    @Override
    protected void processTrigger(Trigger trigger) {
        if (dataProviders.isEmpty()) {
            LOGGER.warn("No data providers in validator: " + this);
        } else {
            // Process data from all providers
            for (DataProvider<RI> dataProvider : dataProviders) {
                processData(dataProvider.getData());
            }
        }
    }

    /**
     * Validates the specified data all rules.
     *
     * @param data Data to be validated against all rules.
     */
    protected void processData(RI data) {
        // Check data against all rules
        Collection<RO> results = new ArrayList<RO>(resultHandlers.size());
        for (Rule<RI, RO> rule : rules) {
            results.add(rule.validate(data));
        }

        // Aggregate all results and process the output
        RHI aggregatedResult = resultAggregator.transform(results);
        processResult(aggregatedResult);
    }

    /**
     * Handles the specified aggregated result using all result handlers.
     *
     * @param aggregatedResult Aggregated result to be processed by all result handlers.
     */
    protected void processResult(RHI aggregatedResult) {
        // Process the result with all result handlers
        for (ResultHandler<RHI> resultHandler : resultHandlers) {
            resultHandler.handleResult(aggregatedResult);
        }
    }
}
