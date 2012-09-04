/*
 * Copyright (c) 2012, Patrick Moawad
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

package com.github.validationframework.base.transform;

import java.util.Collection;

/**
 * Transforms a collection of boolean values into a single boolean value by aggregating with the boolean OR operator.
 *
 * @see Transformer
 */
public class OrBooleanAggregator implements Transformer<Collection<Boolean>, Boolean> {

	/**
	 * Default boolean value to be used when transforming an empty or null collection.
	 */
	public static final boolean DEFAULT_EMPTY_COLLECTION_VALUE = true;

	/**
	 * Default boolean value to be used when transforming a null value from the collection.
	 */
	public static final boolean DEFAULT_NULL_ELEMENT_VALUE = false;

	/**
	 * Boolean value to be used when transforming an empty collection.
	 */
	private final boolean emptyCollectionValid;

	/**
	 * Boolean value to be used when transforming a null value from the collection.
	 */
	private final boolean nullElementValid;

	/**
	 * Default constructor using values for empty and null collections, and null elements.
	 *
	 * @see #DEFAULT_EMPTY_COLLECTION_VALUE
	 * @see #DEFAULT_NULL_ELEMENT_VALUE
	 */
	public OrBooleanAggregator() {
		this(DEFAULT_EMPTY_COLLECTION_VALUE, DEFAULT_NULL_ELEMENT_VALUE);
	}

	/**
	 * Constructor specifying the boolean values for empty and null collections, and null elements.
	 *
	 * @param emptyCollectionValue Value for empty and null collections.
	 * @param nullElementValue Value for null elements in the transformed collection.
	 */
	public OrBooleanAggregator(final boolean emptyCollectionValue, final boolean nullElementValue) {
		this.emptyCollectionValid = emptyCollectionValue;
		this.nullElementValid = nullElementValue;
	}

	/**
	 * @see Transformer#transform(Object)
	 */
	@Override
	public Boolean transform(final Collection<Boolean> elements) {
		Boolean aggregation = false;

		if ((elements == null) || elements.isEmpty()) {
			aggregation = emptyCollectionValid;
		} else {
			for (final Boolean element : elements) {
				Boolean result = element;
				if (result == null) {
					result = nullElementValid;
				}
				aggregation |= result;
				if (aggregation) {
					break;
				}
			}
		}

		return aggregation;
	}
}