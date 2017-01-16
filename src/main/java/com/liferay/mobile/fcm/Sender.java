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

import com.liferay.mobile.fcm.json.Json;

import java.io.Reader;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Bruno Farache
 */

public class Sender {

	public static final String AUTHORIZATION = "Authorization";

	public static final String URL = "https://fcm.googleapis.com/fcm/send";

	public Sender(String key) {
		this(key, URL);
	}

	public Status send(Message message) throws Exception {
		Request request = createRequest(message);
		Response response = client.newCall(request).execute();

		Reader body = response.body().charStream();
		Status status = statusFactory.createStatus(message, body);
		status.httpResponse(response);

		return status;
	}

	public String key() {
		return key;
	}

	public String url() {
		return url;
	}

	public Sender statusFactory(StatusFactory statusFactory) {
		this.statusFactory = statusFactory;
		return this;
	}

	protected Sender(String key, String url) {
		this.client = new OkHttpClient();
		this.key = key;
		this.url = url;
	}

	protected Request createRequest(Message message) {
		RequestBody body = RequestBody.create(
			contentType, Json.toJson(message));

		return new Request.Builder()
			.url(url)
			.header(AUTHORIZATION, "key=" + key)
			.post(body)
			.build();
	}

	protected final OkHttpClient client;

	protected final MediaType contentType = MediaType.parse("application/json");

	protected final String key;

	protected StatusFactory statusFactory = new StatusFactory();

	protected final String url;

}