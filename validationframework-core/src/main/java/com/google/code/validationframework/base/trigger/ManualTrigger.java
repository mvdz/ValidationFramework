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

package com.google.code.validationframework.base.trigger;

import com.google.code.validationframework.api.trigger.TriggerEvent;

/**
 * Trigger allowing the programmer to fire trigger events using a single method call.
 * <p>
 * This is for convenience purposes as it allows to trigger validation at convenient times without having to implement a
 * custom {@link com.google.code.validationframework.api.trigger.Trigger}. It can be used, for instance, to trigger an
 * initial validation when a dialog pops up, or when some other events occur in the system.
 *
 * @see AbstractTrigger
 */
public class ManualTrigger extends AbstractTrigger {

    /**
     * {@inheritDoc}
     *
     * @deprecated Use {@link #trigger()} instead.<br>This method will be removed in future releases.
     */
    @Deprecated
    public void fireTriggerEvent() {
        trigger();
    }

    /**
     * Fires a default trigger event whose source is this trigger.
     *
     * @see #trigger(TriggerEvent)
     * @see TriggerEvent
     */
    public void trigger() {
        super.fireTriggerEvent(new TriggerEvent(this));
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated Use {@link #trigger(TriggerEvent)} instead.<br>This method will be made protected again in future
     * releases.
     */
    @Deprecated
    @Override
    public void fireTriggerEvent(TriggerEvent event) {
        super.fireTriggerEvent(event);
    }

    /**
     * {@inheritDoc} Fires the specified trigger event.<br>This method, from the super class, has been made public for
     * convenience.
     *
     * @see AbstractTrigger#fireTriggerEvent(TriggerEvent)
     * @see TriggerEvent
     */
    public void trigger(TriggerEvent event) {
        super.fireTriggerEvent(event);
    }
}
