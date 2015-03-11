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

package com.google.code.validationframework.swing.resulthandler.bool;

import com.google.code.validationframework.swing.resulthandler.AbstractComponentResultHandler;

import java.awt.Component;

/**
 * Result handler enabling (respectively disabling) one or several components when the result is valid (respectively
 * invalid).
 */
public class ComponentEnablingBooleanResultHandler extends AbstractComponentResultHandler<Boolean> {

    /**
     * @see AbstractComponentResultHandler#AbstractComponentResultHandler()
     */
    public ComponentEnablingBooleanResultHandler() {
        super();
    }

    /**
     * @see AbstractComponentResultHandler#AbstractComponentResultHandler(Component...)
     */
    public ComponentEnablingBooleanResultHandler(final Component... components) {
        super(components);
    }

    /**
     * @see AbstractComponentResultHandler#handleResult(Object)
     */
    @Override
    public void handleResult(final Boolean result) {
        boolean enabled = false;
        if (result != null) {
            enabled = result;
        }

        for (final Component component : components) {
            component.setEnabled(enabled);
        }
    }
}
