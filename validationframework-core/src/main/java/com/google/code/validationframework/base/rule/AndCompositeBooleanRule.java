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

package com.google.code.validationframework.base.rule;

import com.google.code.validationframework.api.common.Disposable;
import com.google.code.validationframework.api.rule.Rule;
import com.google.code.validationframework.api.transform.Transformer;
import com.google.code.validationframework.base.transform.AndBooleanAggregator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Composite rule checking data of a known specific type using sub-rules, and returning a boolean as an aggregation of
 * the boolean results from its sub-rules.
 *
 * @param <RI> Type of data to be validated.<br> It can be, for instance, the type of data handled by a component, or
 *             the type of the component itself.
 * @see AbstractCompositeRule
 * @see OrCompositeBooleanRule
 */
public class AndCompositeBooleanRule<RI> extends AbstractCompositeRule<RI, Boolean> {

    /**
     * Boolean aggregator using the AND operator.
     *
     * @see AndBooleanAggregator
     */
    private final Transformer<Collection<Boolean>, Boolean> aggregator = new AndBooleanAggregator();

    /**
     * @see AbstractCompositeRule#AbstractCompositeRule()
     */
    public AndCompositeBooleanRule() {
        super();
    }

    /**
     * @see AbstractCompositeRule#AbstractCompositeRule(Rule[])
     */
    public AndCompositeBooleanRule(Rule<? super RI, Boolean>... rules) {
        super(rules);
    }

    /**
     * @see AbstractCompositeRule#validate(Object)
     */
    @Override
    public Boolean validate(RI data) {
        // Collect results
        Collection<Boolean> results = new ArrayList<Boolean>();
        for (Rule<? super RI, ? extends Boolean> rule : rules) {
            Boolean result = rule.validate(data);
            results.add(result);
        }

        // Aggregate results
        return aggregator.transform(results);
    }

    /**
     * @see AbstractCompositeRule#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();

        if (aggregator instanceof Disposable) {
            ((Disposable) aggregator).dispose();
        }
    }
}
