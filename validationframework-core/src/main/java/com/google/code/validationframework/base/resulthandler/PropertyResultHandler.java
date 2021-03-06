/*
 * Copyright (c) 2017, ValidationFramework Authors
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

import com.google.code.validationframework.api.property.WritableProperty;
import com.google.code.validationframework.api.resulthandler.ResultHandler;

/**
 * Result handler set the value of a {@link WritableProperty} with the validation result.
 *
 * @param <RHI> Type of validation result to be set on the property.
 *
 * @see WritableProperty
 */
public class PropertyResultHandler<RHI> implements ResultHandler<RHI> {

    /**
     * Property to be set with the validation result.
     */
    private final WritableProperty<RHI> property;

    /**
     * Constructor specifying the property to be set with the validation result.
     *
     * @param property Property to be set with the validation result.
     */
    public PropertyResultHandler(WritableProperty<RHI> property) {
        this.property = property;
    }

    /**
     * @see ResultHandler#handleResult(Object)
     * @see WritableProperty#setValue(Object)
     */
    @Override
    public void handleResult(RHI result) {
        property.setValue(result);
    }
}
