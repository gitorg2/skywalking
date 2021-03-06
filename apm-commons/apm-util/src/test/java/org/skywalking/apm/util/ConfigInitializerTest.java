/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.util;

import java.util.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wusheng on 2017/2/27.
 */
public class ConfigInitializerTest {
    @Test
    public void testInitialize() throws IllegalAccessException {
        Properties properties = new Properties();
        properties.put("Level1Object.strAttr".toLowerCase(), "stringValue");
        properties.put("Level1Object.Level2Object.intAttr".toLowerCase(), "1000");
        properties.put("Level1Object.Level2Object.longAttr".toLowerCase(), "1000");
        properties.put("Level1Object.Level2Object.booleanAttr".toLowerCase(), "true");

        ConfigInitializer.initialize(properties, TestPropertiesObject.class);

        Assert.assertEquals("stringValue", TestPropertiesObject.Level1Object.strAttr);
        Assert.assertEquals(1000, TestPropertiesObject.Level1Object.Level2Object.intAttr);
        Assert.assertEquals(1000L, TestPropertiesObject.Level1Object.Level2Object.longAttr);
        Assert.assertEquals(true, TestPropertiesObject.Level1Object.Level2Object.booleanAttr);
    }

    @Test
    public void testInitializeWithUnsupportedConfig() throws IllegalAccessException {
        Properties properties = new Properties();
        properties.put("Level1Object.noExistAttr".toLowerCase(), "stringValue");

        ConfigInitializer.initialize(properties, TestPropertiesObject.class);

        Assert.assertNull(TestPropertiesObject.Level1Object.strAttr);
    }

    @Before
    public void clear() {
        TestPropertiesObject.Level1Object.strAttr = null;
        TestPropertiesObject.Level1Object.Level2Object.intAttr = 0;
        TestPropertiesObject.Level1Object.Level2Object.longAttr = 0;
        TestPropertiesObject.Level1Object.Level2Object.booleanAttr = false;
    }

    public static class TestPropertiesObject {
        public static class Level1Object {
            public static String strAttr = null;

            public static class Level2Object {
                public static int intAttr = 0;

                public static long longAttr;

                public static boolean booleanAttr;
            }
        }
    }
}
