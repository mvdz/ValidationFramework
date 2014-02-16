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

import com.google.code.validationframework.api.binding.ReadableProperty;
import com.google.code.validationframework.api.binding.WritableProperty;
import com.google.code.validationframework.base.transform.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility class that can be used to help binding properties and transform their values.
 * <p/>
 * This binder utility will create {@link Bond}s between properties. These bonds can be broken by calling their {@link
 * Bond#dispose()} method.
 * <p/>
 * If you are using JavaFX, you should better use JavaFX's property binding mechanism. The binding mechanism provided by
 * the ValidationFramework is very simple and mostly meant for Swing and other frameworks that can benefit from it.
 * JavaFX has a much more furnished API to achieve similar goals and much more.
 *
 * @see ReadableProperty
 * @see WritableProperty
 * @see SingleMasterBond
 * @see MultipleMasterBond
 */
public final class Binder {

    /**
     * Builder class that is part of the DSL for binding properties.
     *
     * @param <MO> Type of data that can be read from the master property.
     * @param <SI> Type of data that can be written to slave properties.
     */
    public static class SingleMasterBinding<MO, SI> {

        /**
         * Master property.
         */
        private final ReadableProperty<MO> master;

        /**
         * Master property value transformers.
         */
        private final List<Transformer<?, ?>> transformers = new ArrayList<Transformer<?, ?>>();

        /**
         * Constructor specifying the master property to be bound and the transformers to be applied.
         *
         * @param master       Master property that are part of the bind.
         * @param transformers Transformers to be applied.
         */
        public SingleMasterBinding(ReadableProperty<MO> master, Collection<Transformer<?, ?>> transformers) {
            this.master = master;
            if (transformers != null) {
                this.transformers.addAll(transformers);
            }
        }

        /**
         * Specifies a transformer to be used to transform the master property value.
         *
         * @param transformer Transformer to be used by the bond.
         * @param <TSI>       Type of output of the specified transformer.
         *
         * @return Builder object to continue building the bond.
         */
        public <TSI> SingleMasterBinding<MO, TSI> transform(Transformer<SI, TSI> transformer) {
            transformers.add(transformer);
            return new SingleMasterBinding<MO, TSI>(master, transformers);
        }

        /**
         * Specifies the slave property that is part of the bind and creates the bond between the master and the slave.
         *
         * @param slave Slave property.
         *
         * @return Bond between the master and the slave.
         */
        public SingleMasterBond<MO, SI> to(WritableProperty<SI> slave) {
            return new SingleMasterBond<MO, SI>(master, transformers, Collections.singleton(slave));
        }

        /**
         * Specifies the slave properties that are part of the bind and creates the bond between the master and the
         * slaves.
         *
         * @param slaves Slave properties.
         *
         * @return Bond between the master and the slaves.
         */
        public SingleMasterBond<MO, SI> to(Collection<WritableProperty<SI>> slaves) {
            return new SingleMasterBond<MO, SI>(master, transformers, slaves);
        }

        /**
         * Specifies the slave properties that are part of the bind and creates the bond between the master and the
         * slaves.
         *
         * @param slaves Slave properties.
         *
         * @return Bond between the master and the slaves.
         */
        public SingleMasterBond<MO, SI> to(WritableProperty<SI>... slaves) {
            return to(Arrays.asList(slaves));
        }
    }

    /**
     * Builder class that is part of the DSL for binding properties.
     *
     * @param <MO> Type of data that can be read from the master properties.
     * @param <SI> Type of data that can be written to slave properties.
     */
    public static class MultipleMasterBinding<MO, SI> {

        /**
         * Master properties.
         */
        private final Collection<ReadableProperty<MO>> masters;

        /**
         * Master properties values transformers.
         */
        private final List<Transformer<?, ?>> transformers = new ArrayList<Transformer<?, ?>>();

        /**
         * Constructor specifying the master properties to be bound and the transformers to be applied.
         *
         * @param masters      Master properties that are part of the bind.
         * @param transformers Transformers to be applied.
         */
        public MultipleMasterBinding(Collection<ReadableProperty<MO>> masters, Collection<Transformer<?,
                ?>> transformers) {
            this.masters = masters;
            if (transformers != null) {
                this.transformers.addAll(transformers);
            }
        }

        /**
         * Specifies a transformer to be used to transform the collection of master properties values.
         *
         * @param transformer Transformer to be used by the bond.
         * @param <TSI>       Type of output of the specified transformer.
         *
         * @return Builder object to continue building the bond.
         */
        public <TSI> MultipleMasterBinding<MO, TSI> transform(Transformer<SI, TSI> transformer) {
            transformers.add(transformer);
            return new MultipleMasterBinding<MO, TSI>(masters, transformers);
        }

        /**
         * Specifies the slave property that is part of the bind and creates the bond between the masters and the slave.
         *
         * @param slave Slave property.
         *
         * @return Bond between the masters and the slave.
         */
        public MultipleMasterBond<MO, SI> to(WritableProperty<SI> slave) {
            return new MultipleMasterBond<MO, SI>(masters, transformers, Collections.singleton(slave));
        }

        /**
         * Specifies the slave properties that are part of the bind and creates the bond between the masters and the
         * slaves.
         *
         * @param slaves Slave properties.
         *
         * @return Bond between the masters and the slaves.
         */
        public MultipleMasterBond<MO, SI> to(Collection<WritableProperty<SI>> slaves) {
            return new MultipleMasterBond<MO, SI>(masters, transformers, slaves);
        }

        /**
         * Specifies the slave properties that are part of the bind and creates the bond between the masters and the
         * slaves.
         *
         * @param slaves Slave properties.
         *
         * @return Bond between the masters and the slaves.
         */
        public MultipleMasterBond<MO, SI> to(WritableProperty<SI>... slaves) {
            return to(Arrays.asList(slaves));
        }
    }

    /**
     * Private constructor for utility class.
     */
    private Binder() {
        // Nothing to be done
    }

    /**
     * Specifies the master property that is part of the binding.
     *
     * @param master Master property.
     * @param <MO>   Type of value to be read from the master property.
     *
     * @return DSL object.
     */
    public static <MO> SingleMasterBinding<MO, MO> from(ReadableProperty<MO> master) {
        return new SingleMasterBinding<MO, MO>(master, null);
    }

    /**
     * Specifies the master properties that are part of the binding.
     *
     * @param masters Master properties.
     * @param <MO>    Type of value to be read from the master properties.
     *
     * @return DSL object.
     */
    public static <MO> MultipleMasterBinding<MO, Collection<MO>> from(Collection<ReadableProperty<MO>> masters) {
        return new MultipleMasterBinding<MO, Collection<MO>>(masters, null);
    }

    /**
     * Specifies the master properties that are part of the binding.
     *
     * @param masters Master properties.
     * @param <MO>    Type of value to be read from the master properties.
     *
     * @return DSL object.
     */
    public static <MO> MultipleMasterBinding<MO, Collection<MO>> from(ReadableProperty<MO>... masters) {
        return new MultipleMasterBinding<MO, Collection<MO>>(Arrays.asList(masters), null);
    }
}