package com.fasterxml.jackson.databind.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.BaseMapTest;
import com.fasterxml.jackson.databind.ObjectMapper;

// [databind#5967] Records take a different path in
// POJOPropertiesCollector#_renameProperties (always skipped by isRecordType()
// when the property name is in _ignoredPropertyNames), so the field-stripping
// fix added for non-records must not regress record renaming.
public class RecordRenamedPropertyIgnoreFieldBypass5967Test extends BaseMapTest
{
    public record RenamedRecord(@JsonProperty("renamedProp") String prop) {}

    private final ObjectMapper MAPPER = newJsonMapper();

    public void testRecordWithRenamedPropertyRoundTrips() throws Exception
    {
        RenamedRecord original = new RenamedRecord("someValue");
        String json = MAPPER.writeValueAsString(original);
        assertEquals("{\"renamedProp\":\"someValue\"}", json);

        RenamedRecord result = MAPPER.readValue(json, RenamedRecord.class);
        assertEquals("someValue", result.prop());
    }
}
