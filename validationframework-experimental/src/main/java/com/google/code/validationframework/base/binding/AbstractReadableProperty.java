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
import com.google.code.validationframework.base.utils.ValueUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of a {@link ReadableProperty}.
 * <p/>
 * Sub-classes should call the {@link #notifyListeners(Object, Object)} method whenever the property value changes.
 * <p/>
 * Note that this class is not thread-safe.
 *
 * @param <T> Type of property value.
 */
public abstract class AbstractReadableProperty<T> implements ReadableProperty<T> {

    /**
     * Writable properties to be updated.
     */
    private final List<ChangeListener<T>> listeners = new
            ArrayList<ChangeListener<T>>();

    /**
     * @see ReadableProperty#addChangeListener(ChangeListener)
     */
    @Override
    public void addChangeListener(ChangeListener<T> listener) {
        listeners.add(listener);
        listener.propertyChanged(this, null, getValue());
    }

    /**
     * @see ReadableProperty#removeChangeListener(ChangeListener)
     */
    @Override
    public void removeChangeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Updates the slaved writable properties with this property's value.
     *
     * @see #getValue()
     */
    protected void notifyListeners(T oldValue, T newValue) {
        notifyListeners(oldValue, newValue, false);
    }

    protected void notifyListeners(T oldValue, T newValue, boolean evenIfNoChange) {
        if (evenIfNoChange || !ValueUtils.areEqual(oldValue, newValue)) {
            for (ChangeListener<T> listener : listeners) {
                listener.propertyChanged(this, oldValue, newValue);
            }
        }
    }
}
