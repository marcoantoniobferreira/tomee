/*
 *     Licensed to the Apache Software Foundation (ASF) under one or more
 *     contributor license agreements.  See the NOTICE file distributed with
 *     this work for additional information regarding copyright ownership.
 *     The ASF licenses this file to You under the Apache License, Version 2.0
 *     (the "License"); you may not use this file except in compliance with
 *     the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package org.apache.tomee.microprofile.jwt.cdi;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;

public class JsonValueProducer {

    @Inject
    private MPJWTProducer producer;

    @Produces
    @Claim("")
    public JsonString getJsonString(final InjectionPoint ip) {
        return getValue(ip);
    }

    @Produces
    @Claim("")
    public Optional<JsonString> getOptionalJsonString(final InjectionPoint ip) {
        return getOptionalValue(ip);
    }

    @Produces
    @Claim("")
    public JsonNumber getJsonNumber(final InjectionPoint ip) {
        return getValue(ip);
    }

    @Produces
    @Claim("")
    public Optional<JsonNumber> getOptionalJsonNumber(final InjectionPoint ip) {
        return getOptionalValue(ip);
    }

    @Produces
    @Claim("")
    public JsonArray getJsonArray(final InjectionPoint ip) {
        return getValue(ip);
    }

    @Produces
    @Claim("")
    public Optional<JsonArray> getOptionalJsonArray(final InjectionPoint ip) {
        return getOptionalValue(ip);
    }

    @Produces
    @Claim("")
    public JsonObject getJsonObject(final InjectionPoint ip) {
        return getValue(ip);
    }

    @Produces
    @Claim("")
    public Optional<JsonObject> getOptionalJsonObject(final InjectionPoint ip) {
        return getOptionalValue(ip);
    }

    public <T extends JsonValue> T getValue(final InjectionPoint ip) {
        String name = getName(ip);
        T jsonValue = (T) producer.generalJsonValueProducer(name);
        return jsonValue;
    }

    public <T extends JsonValue> Optional<T> getOptionalValue(final InjectionPoint ip) {
        String name = getName(ip);
        T jsonValue = (T) producer.generalJsonValueProducer(name);
        return Optional.ofNullable(jsonValue);
    }

    String getName(final InjectionPoint ip) {
        String name = null;
        for (Annotation ann : ip.getQualifiers()) {
            if (ann instanceof Claim) {
                Claim claim = (Claim) ann;
                name = claim.standard() == Claims.UNKNOWN ? claim.value() : claim.standard().name();
            }
        }
        return name;
    }
}