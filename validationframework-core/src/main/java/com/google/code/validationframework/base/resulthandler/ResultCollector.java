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

package com.google.code.validationframework.base.resulthandler;

import com.google.code.validationframework.api.common.Disposable;
import com.google.code.validationframework.api.dataprovider.DataProvider;
import com.google.code.validationframework.api.resulthandler.ResultHandler;
import com.google.code.validationframework.api.transform.Transformer;
import com.google.code.validationframework.api.trigger.TriggerEvent;
import com.google.code.validationframework.base.transform.CastTransformer;
import com.google.code.validationframework.base.trigger.AbstractTrigger;

/**
 * Result collector providing the result from one validator as validation input to another validator.
 * <p/>
 * So this collector is meant to be registered as a result handler to one validator, and as a trigger and data provider
 * to one or several other validators.
 * <p/>
 * This can be useful to aggregate the validation results from a group in independent fields, for instance to enable or
 * disable an Apply button on a dialog.
 *
 * @param <VO>  Type of result collected from another validator.
 * @param <DPO> Type of data provided by the collector.<br>
 *              It could be the same as VO.
 *
 * @see ResultHandler
 * @see AbstractTrigger
 * @see DataProvider
 * @see Disposable
 */
public class ResultCollector<VO, DPO> extends AbstractTrigger implements ResultHandler<VO>, DataProvider<DPO>,
        Disposable {

    /**
     * Last collected result.
     */
    protected VO lastResult = null;

    /**
     * Transformer to apply on the collected result before providing it.
     */
    protected final Transformer<VO, DPO> transformer;

    /**
     * Default constructor using the simple cast transformer.
     *
     * @see CastTransformer
     */
    public ResultCollector() {
        this(new CastTransformer<VO, DPO>());
    }

    /**
     * Constructor specifying the transformer to apply on the collected result before providing it.
     *
     * @param transformer Transform to apply on the collected result.
     */
    public ResultCollector(Transformer<VO, DPO> transformer) {
        super();
        this.transformer = transformer;
    }

    /**
     * @see ResultHandler#handleResult(Object)
     */
    @Override
    public void handleResult(VO result) {
        lastResult = result; // We expect the method getData() to be called subsequently
        fireTriggerEvent(new TriggerEvent(this));
    }

    /**
     * @see DataProvider#getData()
     */
    @Override
    public DPO getData() {
        DPO transformedData = null;

        if (transformer != null) {
            transformedData = transformer.transform(lastResult);
        }

        return transformedData;
    }

    /**
     * @see Disposable#dispose()
     */
    @Override
    public void dispose() {
        if (transformer instanceof Disposable) {
            ((Disposable) transformer).dispose();
        }
    }
}
