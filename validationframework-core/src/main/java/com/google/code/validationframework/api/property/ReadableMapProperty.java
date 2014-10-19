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

package com.google.code.validationframework.api.property;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Interface to be implemented by readable map properties.
 * <p/>
 * Note that most of the methods are based on the {@link java.util.Map} interface.
 *
 * @param <K> Type of keys maintained by this map property.
 * @param <R> Type of values that can be read from this map property.
 */
public interface ReadableMapProperty<K, R> {

    /**
     * Adds the specified map value change listener.
     *
     * @param listener Listener to be added.
     */
    void addValueChangeListener(MapValueChangeListener<K, R> listener);

    /**
     * Removes the specified map value change listener.
     *
     * @param listener Listener to be removed.
     */
    void removeValueChangeListener(MapValueChangeListener<K, R> listener);

    /**
     * @see Map#size()
     */
    int size();

    /**
     * @see Map#isEmpty()
     */
    boolean isEmpty();

    /**
     * @see Map#containsKey(Object)
     */
    boolean containsKey(Object key);

    /**
     * @see Map#containsValue(Object)
     */
    boolean containsValue(Object value);

    /**
     * @see Map#get(Object)
     */
    R get(Object key);

    /**
     * @see Map#keySet()
     */
    Set<K> keySet();

    /**
     * @see Map#values()
     */
    Collection<R> values();

    /**
     * @see Map#entrySet()
     */
    Set<Map.Entry<K, R>> entrySet();
}
