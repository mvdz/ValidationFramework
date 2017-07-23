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

package com.google.code.validationframework.swing.decoration.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * Utility class for handling icons.
 */
public final class IconUtils {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IconUtils.class);

    public static final ImageIcon INFO_ICON = loadImageIcon("/images/defaults/info.png",
            IconUtils.class);
    public static final ImageIcon INVALID_ICON = loadImageIcon("/images/defaults/invalid.png",
            IconUtils.class);
    public static final ImageIcon VALID_ICON = loadImageIcon("/images/defaults/valid.png",
            IconUtils.class);
    public static final ImageIcon WARNING_ICON = loadImageIcon("/images/defaults/warning.png",
            IconUtils.class);

    /**
     * Private constructor for utility class.
     */
    private IconUtils() {
        // Nothing to be done
    }

    /**
     * Loads an image icon from a resource file.
     *
     * @param iconName Name of the resource file to be loaded.
     * @param clazz    Class for which the resource exists.
     *
     * @return Image icon if it could be loaded, null otherwise.
     */
    public static ImageIcon loadImageIcon(final String iconName, final Class<?> clazz) {
        ImageIcon icon = null;

        if (iconName != null) {
            final URL iconResource = clazz.getResource(iconName);
            if (iconResource == null) {
                LOGGER.error("Icon could not be loaded: '" + iconName);
                icon = null;
            } else {
                icon = new ImageIcon(iconResource);
            }
        }

        return icon;
    }
}
