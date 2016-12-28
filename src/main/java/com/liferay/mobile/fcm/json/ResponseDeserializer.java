/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.fcm.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.liferay.mobile.fcm.Response;

import com.google.gson.JsonDeserializer;
import com.liferay.mobile.fcm.Result;

import java.lang.reflect.Type;

/**
 * @author Bruno Farache
 */
public class ResponseDeserializer implements JsonDeserializer<Response> {

	public static final String ERROR = "error";

	public static final String MESSAGE_ID = "message_id";

	@Override
	public Response deserialize(
			JsonElement json, Type type, JsonDeserializationContext context)
		throws JsonParseException {

		if (isTopicResponse(json)) {
			return createTopicResponse(json);
		}

		return gson().fromJson(json.getAsJsonObject(), Response.class);
	}

	protected Response createTopicResponse(JsonElement json) {
		JsonObject root = json.getAsJsonObject();
		Response.Builder builder = new Response.Builder();

		if (root.has(ERROR)) {
			Result result = new Result.Builder()
				.error(root.get(ERROR).getAsString())
				.build();

			builder
				.numberOfFailedMessages(1)
				.result(result);
		}
		else {
			Result result = new Result.Builder()
				.messageId(root.get(MESSAGE_ID).getAsString())
				.build();

			builder
				.numberOfSucceededMessages(1)
				.result(result);
		}

		return builder.build();
	}

	protected boolean isTopicResponse(JsonElement json) {
		JsonObject root = json.getAsJsonObject();
		return (root.has(MESSAGE_ID) || root.has(ERROR)) ;
	}

	protected static Gson gson() {
		if (gson == null) {
			gson = Json.createDefaultGsonBuilder().create();
		}

		return gson;
	}

	protected static Gson gson;

}