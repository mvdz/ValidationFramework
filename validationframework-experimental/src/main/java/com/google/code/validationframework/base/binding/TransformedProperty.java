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

package com.google.code.validationframework.base.binding;

import com.google.code.validationframework.api.binding.WritableProperty;
import com.google.code.validationframework.base.transform.Transformer;
import com.google.code.validationframework.base.utils.ValueUtils;

/**
 * Implementation of a readable and writable property whose output is the result of the transformation of the input.
 * <p/>
 * Slaved properties will only be updated if the new value set on this property differs from the previous one.
 * <p/>
 * Note that binding can be bi-directional. Infinite recursion will be prevented.
 * <p/>
 * Finally note that this class is not thread-safe.
 *
 * @param <I>
 * @param <O>
 */
public class TransformedProperty<I, O> extends AbstractReadableWritableProperty<I, O> {

    /**
     * Transformer to be used to transform input values.
     */
    private final Transformer<I, O> transformer;

    /**
     * Property output value.
     */
    private O value = null;

    /**
     * Flag used to avoid any infinite recursion.
     */
    private boolean settingValue = false;

    /**
     * Constructor specifying the transformer to be used to transform input values, using null as the initial input
     * value.
     *
     * @param transformer Transformer to transform input values.
     */
    public TransformedProperty(Transformer<I, O> transformer) {
        this(transformer, null);
    }

    /**
     * Constructor specifying the transformer to be used to transform input values, and the initial input value.
     *
     * @param transformer Transformer to transform input values.
     * @param value       Initial property value.
     */
    public TransformedProperty(Transformer<I, O> transformer, I value) {
        this.transformer = transformer;
        this.value = transformer.transform(value);
    }

    /**
     * @see AbstractReadableProperty#getValue()
     */
    @Override
    public O getValue() {
        return value;
    }

    /**
     * @see WritableProperty#setValue(Object)
     */
    @Override
    public void setValue(I value) {
        if (!settingValue) {
            settingValue = true;

            // Update slaves only if the new value is different than the previous value
            O newValue = transformer.transform(value);
            if (!ValueUtils.areEqual(this.value, newValue)) {
                O oldValue = this.value;
                this.value = newValue;
                notifyListeners(oldValue, newValue);
            }

            settingValue = false;
        }
    }
}
