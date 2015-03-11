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

package com.google.code.validationframework.javafx.resulthandler;

import com.google.code.validationframework.api.resulthandler.ResultHandler;
import javafx.beans.value.WritableValue;

/**
 * Result handler setting the result in a specified {@link WritableValue}.
 * <p/>
 * This can be useful, for instance, to update a property with the (possibly transformed) result.
 *
 * @param <RHI>
 */
public class WritableValueSetterResultHandler<RHI> implements ResultHandler<RHI> {

    /**
     * Writable value to be updated with the result handler input.
     */
    private final WritableValue<RHI> writableValue;

    /**
     * Value to be set in the writable value in case of a null result handler input.
     */
    private final RHI valueForNullResult;

    /**
     * Constructor specifying the writable value to be updated.
     * <p/>
     * By default, null will be set in the writable value in case of a null result handler input.
     *
     * @param writableValue Writable value to be updated with the result handler input.
     *
     * @see #WritableValueSetterResultHandler(WritableValue, Object)
     */
    public WritableValueSetterResultHandler(WritableValue<RHI> writableValue) {
        this(writableValue, null);
    }

    /**
     * Constructor specifying the writable value to be updated and the value to be set in case of a null result handler
     * input.
     *
     * @param writableValue      Writable value to be update with the result handler input.
     * @param valueForNullResult Value to be set in case of a null result handler input.
     *
     * @see #WritableValueSetterResultHandler(WritableValue)
     */
    public WritableValueSetterResultHandler(WritableValue<RHI> writableValue, RHI valueForNullResult) {
        this.writableValue = writableValue;
        this.valueForNullResult = valueForNullResult;
    }

    /**
     * @see ResultHandler#handleResult(Object)
     */
    @Override
    public void handleResult(RHI result) {
        RHI valueToBetSet;

        if (result == null) {
            valueToBetSet = valueForNullResult;
        } else {
            valueToBetSet = result;
        }

        writableValue.setValue(valueToBetSet);
    }
}
