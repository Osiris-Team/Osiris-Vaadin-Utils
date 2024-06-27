package com.osiris.osiris_vaadin_utils;

/*-
 * #%L
 * Osiris-Vaadin-Utils
 * %%
 * Copyright (C) 2021 - 2024 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.logging.Level;
import java.util.logging.Logger;

public class AL {
    private static final Logger logger = Logger.getLogger("Osiris-Vaadin-Utils");

    public static void info(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static void debug(Class<?> clazz, String msg) {
        logger.log(Level.INFO, "("+clazz.getSimpleName()+") "+msg);
    }

    public static void warn(Exception e) {
        logger.log(Level.WARNING, e.getMessage(), e);
    }
}
