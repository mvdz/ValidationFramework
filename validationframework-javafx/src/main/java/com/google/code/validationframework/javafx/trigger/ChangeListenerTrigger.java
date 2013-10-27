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

package com.google.code.validationframework.javafx.trigger;

import com.google.code.validationframework.api.trigger.TriggerEvent;
import com.google.code.validationframework.base.trigger.AbstractTrigger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Trigger listening that can be added as a {@link ChangeListener} on an {@link ObservableValue} and triggering the
 * validation whenever the value changes.
 *
 * @param <T> Type of value whose change is being observed.
 *
 * @see AbstractTrigger
 * @see ChangeListener
 * @see ObservableValue
 * @see InvalidationListenerTrigger
 * @see ObservableValueChangeTrigger
 */
public class ChangeListenerTrigger<T> extends AbstractTrigger implements ChangeListener<T> {

    /**
     * @see ChangeListener#changed(ObservableValue, Object, Object)
     */
    @Override
    public void changed(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
        fireTriggerEvent(new TriggerEvent(observableValue));
    }
}