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

import com.google.code.validationframework.base.transform.AndBooleanAggregator;
import com.google.code.validationframework.base.transform.ToStringTransformer;
import org.junit.Test;

import static com.google.code.validationframework.base.binding.Binder.from;
import static org.junit.Assert.assertEquals;

public class BinderTest {

    @Test
    public void testMasterToSlave() {
        SimpleProperty<Integer> master = new SimpleProperty<Integer>(5);
        SimpleProperty<Integer> slave = new SimpleProperty<Integer>(0);
        Binder.from(master).to(slave);

        assertEquals(Integer.valueOf(5), master.getValue());
        assertEquals(master.getValue(), slave.getValue());

        master.setValue(8);

        assertEquals(Integer.valueOf(8), master.getValue());
        assertEquals(master.getValue(), slave.getValue());
    }

    @Test
    public void testMasterToSlaveWithTransformation() {
        SimpleProperty<Integer> master = new SimpleProperty<Integer>(5);
        SimpleProperty<String> slave = new SimpleProperty<String>("0");
        Binder.from(master).transform(new ToStringTransformer<Integer>()).to(slave);

        assertEquals(Integer.valueOf(5), master.getValue());
        assertEquals("5", slave.getValue());

        master.setValue(8);

        assertEquals(Integer.valueOf(8), master.getValue());
        assertEquals("8", slave.getValue());
    }

    @Test
    public void testMasterToSlaveWithAggregation() {
        BooleanProperty master1 = new BooleanProperty(true);
        BooleanProperty master2 = new BooleanProperty(false);
        BooleanProperty master3 = new BooleanProperty(false);
        BooleanProperty slave = new BooleanProperty();
        from(master1, master2, master3).transform(new AndBooleanAggregator()).to(slave);

        assertEquals(false, slave.getValue());
    }

    @Test
    public void testMasterSlaveLoop() {
        IntegerProperty first = new IntegerProperty(5);
        IntegerProperty second = new IntegerProperty(4);

        // The following should not result in an StackOverflowError
        Binder.from(first).to(second);
        Binder.from(second).to(first);

        first.setValue(12);
        assertEquals(Integer.valueOf(12), second.getValue());

        second.setValue(36);
        assertEquals(Integer.valueOf(36), first.getValue());
    }
}
