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

package com.google.code.validationframework.base.dataprovider;

import com.google.code.validationframework.api.common.Disposable;
import com.google.code.validationframework.api.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Composite data provider returning the data of all the sub-data providers in a list.
 *
 * @param <DPO> Type of data in the list.
 *
 * @see DataProvider
 * @see Disposable
 */
public class ListCompositeDataProvider<DPO> implements DataProvider<List<DPO>>, Disposable {

    /**
     * Sub-data providers
     */
    protected final List<DataProvider<DPO>> dataProviders = new ArrayList<DataProvider<DPO>>();

    /**
     * Default constructor.
     */
    public ListCompositeDataProvider() {
        // Nothing to be done
    }

    /**
     * Constructor specifying the first data providers to be added.
     *
     * @param dataProviders First data providers to be added.
     */
    public ListCompositeDataProvider(DataProvider<DPO>... dataProviders) {
        if (dataProviders != null) {
            for (DataProvider<DPO> dataProvider : dataProviders) {
                addDataProvider(dataProvider);
            }
        }
    }

    /**
     * Constructor specifying the first data providers to be added.
     *
     * @param dataProviders First data providers to be added.
     */
    public ListCompositeDataProvider(Collection<DataProvider<DPO>> dataProviders) {
        if (dataProviders != null) {
            for (DataProvider<DPO> dataProvider : dataProviders) {
                addDataProvider(dataProvider);
            }
        }
    }

    /**
     * Adds the specified data provider
     *
     * @param dataProvider Data provider to be added.
     */
    public void addDataProvider(DataProvider<DPO> dataProvider) {
        dataProviders.add(dataProvider);
    }

    /**
     * Removes the specified data provider.
     *
     * @param dataProvider Data provider to be removed.
     */
    public void removeDataProvider(DataProvider<DPO> dataProvider) {
        dataProviders.remove(dataProvider);
    }

    /**
     * @see DataProvider#getData()
     */
    @Override
    public List<DPO> getData() {
        List<DPO> dataList = new ArrayList<DPO>();

        // Read data from all data providers and put them in the list
        for (DataProvider<DPO> dataProvider : dataProviders) {
            dataList.add(dataProvider.getData());
        }

        return dataList;
    }

    /**
     * @see Disposable#dispose()
     */
    @Override
    public void dispose() {
        for (DataProvider<DPO> dataProvider : dataProviders) {
            if (dataProvider instanceof Disposable) {
                ((Disposable) dataProvider).dispose();
            }
        }

        dataProviders.clear();
    }
}
