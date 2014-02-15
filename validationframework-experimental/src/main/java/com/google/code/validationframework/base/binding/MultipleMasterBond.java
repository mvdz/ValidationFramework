/*
 * Copyright (c) 2014, Patrick Moawad
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

import com.google.code.validationframework.api.binding.ChangeListener;
import com.google.code.validationframework.api.binding.ReadableProperty;
import com.google.code.validationframework.api.binding.WritableProperty;
import com.google.code.validationframework.api.common.Disposable;
import com.google.code.validationframework.base.transform.CastTransformer;
import com.google.code.validationframework.base.transform.Transformer;

import java.util.Collection;

public class MultipleMasterBond<MO, SI> implements Disposable {

    private class MasterAdapter implements ChangeListener<Collection<MO>> {

        private final Transformer<Object, SI> lastTransformer = new CastTransformer<Object, SI>();

        @Override
        public void propertyChanged(ReadableProperty<Collection<MO>> property, Collection<MO> oldValues,
                                    Collection<MO> newValues) {
            // Transform value
            Object transformedValue = newValues; // TODO Handle one master value
            for (Transformer transformer : transformers) {
                transformedValue = transformer.transform(transformedValue);
            }
            SI slaveInputValue = lastTransformer.transform(transformedValue);

            // Notify slave
            slaves.setValue(slaveInputValue);
        }
    }

    private final MasterAdapter masterAdapter = new MasterAdapter();

    private final CompositeReadableProperty<MO> masters;

    private final Collection<Transformer<?, ?>> transformers;

    private final CompositeWritableProperty<SI> slaves;

    public MultipleMasterBond(Collection<ReadableProperty<MO>> masters, Collection<Transformer<?, ?>> transformers,
                              Collection<WritableProperty<SI>> slaves) {
        this(new CompositeReadableProperty<MO>(masters), transformers, new CompositeWritableProperty<SI>(slaves));
    }

    public MultipleMasterBond(CompositeReadableProperty<MO> masters, Collection<Transformer<?, ?>> transformers,
                              CompositeWritableProperty<SI> slaves) {
        this.masters = masters;
        this.transformers = transformers;
        this.slaves = slaves;

        masters.addChangeListener(masterAdapter);

        // Slave initial values
        masterAdapter.propertyChanged(null, null, null);
    }

    @Override
    public void dispose() {
        masters.removeChangeListener(masterAdapter);
    }
}
