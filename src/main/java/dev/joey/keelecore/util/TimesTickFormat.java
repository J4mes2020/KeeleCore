package dev.joey.keelecore.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class TimesTickFormat {

    public static Map<String, Long> nameToTicks = new LinkedHashMap<>();

    static {

        nameToTicks.put("sunrise", 23000L);
        nameToTicks.put("dawn", 23000L);
        nameToTicks.put("daystart", 0L);
        nameToTicks.put("day", 0L);
        nameToTicks.put("morning", 1000L);
        nameToTicks.put("midday", 6000L);
        nameToTicks.put("noon", 6000L);
        nameToTicks.put("afternoon", 9000L);
        nameToTicks.put("sunset", 12000L);
        nameToTicks.put("dusk", 12000L);
        nameToTicks.put("sundown", 12000L);
        nameToTicks.put("nightfall", 12000L);
        nameToTicks.put("nightstart", 14000L);
        nameToTicks.put("night", 14000L);
        nameToTicks.put("midnight", 18000L);

    }

}
