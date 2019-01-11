package org.springframework.social.vkontakte.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.vkontakte.api.VKGenericResponse;
import org.springframework.social.vkontakte.api.VKResponse;
import org.springframework.social.vkontakte.api.VKontakteErrorException;
import org.springframework.social.vkontakte.api.impl.json.VKArray;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * override because of vk required api version param
 * org.springframework.social.vkontakte.api.AbstractVKontakteOperations#API_VERSION
 **/
public class AbstractVKontakteOperations {
    private final static String API_VERSION = "5.0";
    private final static String VK_REST_URL = "https://api.vk.com/method/";

    private final boolean isAuthorized;
    private final String accessToken;
    protected final ObjectMapper objectMapper;

    public AbstractVKontakteOperations(boolean isAuthorized, String accessToken, ObjectMapper objectMapper) {
        this.isAuthorized = isAuthorized;
        this.accessToken = accessToken;
        this.objectMapper = objectMapper;
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException("vkontakte");
        }
    }

    protected URI makeOperationURL(String method, Properties params) {
        URIBuilder uri = URIBuilder.fromUri(VK_REST_URL + method);
        uri.queryParam("v", API_VERSION);
        uri.queryParam("access_token", accessToken);
        for (Map.Entry<Object, Object> objectObjectEntry : params.entrySet()) {
            uri.queryParam(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue().toString());
        }
        return uri.build();
    }

    /* throw exception if VKontakte response contains error */
    // TODO: consider to throw specific exceptions for each error code.
    //       like for error code 113 that would be let's say InvalidUserIdVKException
    protected <T extends VKResponse> void checkForError(T toCheck) {
        if (toCheck.getError() != null) {
            throw new VKontakteErrorException(toCheck.getError());
        }
    }

    protected <T> VKArray<T> deserializeArray(VKGenericResponse response, Class<T> itemClass) {
        checkForError(response);

        Assert.isTrue(response.getResponse().isArray());
        ArrayNode items = (ArrayNode) response.getResponse();
        int count = items.get(0).asInt();
        List<T> elements = new ArrayList<T>();
        for (int i = 1; i < items.size(); i++) {
            try {
                elements.add(objectMapper.readValue(items.get(i).asText(), itemClass));
            } catch (IOException e) {
                throw new UncategorizedApiException("vkontakte", "Error deserializing: " + items.get(i), e);
            }
        }

        return new VKArray<T>(count, elements);
    }
}
