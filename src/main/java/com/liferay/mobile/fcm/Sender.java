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

package com.liferay.mobile.fcm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Bruno Farache
 */

public class Sender {

	public static final String AUTHORIZATION = "Authorization";

	public static final String URL = "https://fcm.googleapis.com/fcm/send";

	public Sender(String key) {
		this(key, URL);
	}

	protected Sender(String key, String url) {
		this.client = new OkHttpClient();
		this.key = key;
		this.url = url;
	}

	public Response send(Message message) throws Exception {
		Request request = createRequest(message);
		okhttp3.Response httpResponse = client.newCall(request).execute();

		Response response = fromJson(
			httpResponse.body().charStream(), Response.class);

		return response.httpResponse(httpResponse);
	}

	protected Request createRequest(Message message) {
		RequestBody body = RequestBody.create(
			contentType, toJson(message));

		return new Request.Builder()
			.url(url)
			.header(AUTHORIZATION, "key=" + key)
			.post(body)
			.build();
	}

	public String key() {
		return key;
	}

	public String url() {
		return url;
	}

	protected static <T> T fromJson(Reader reader, Class<T> clazz) {
		return gson().fromJson(reader, clazz);
	}

	protected static String toJson(Object object) {
		return gson().toJson(object);
	}

	protected static Gson gson() {
		if (gson == null) {
			gson = new GsonBuilder()
				.disableHtmlEscaping()
				.create();
		}

		return gson;
	}

	protected final OkHttpClient client;

	protected final MediaType contentType = MediaType.parse("application/json");

	protected static Gson gson;

	protected final String key;

	protected final String url;

}