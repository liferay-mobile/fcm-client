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

import io.reactivex.Single;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Bruno Farache
 */

public class Sender {

	public Sender(String key) {
		this.client = new OkHttpClient();
		this.key = key;
	}

	public Single<Response> send(Message message) {
		return Single.fromCallable(() ->
			client.newCall(createRequest(message)).execute()
		);
	}

	protected Request createRequest(Message message) {
		RequestBody body = RequestBody.create(
			contentType, gson.toJson(message));

		return new Request.Builder()
			.url(URL)
			.header(AUTHORIZATION, "key=" + key)
			.post(body)
			.build();
	}

	protected static final String AUTHORIZATION = "Authorization";

	protected static final String URL = "https://fcm.googleapis.com/fcm/send";

	protected final OkHttpClient client;

	protected final MediaType contentType = MediaType.parse("application/json");

	protected final Gson gson = new Gson();

	protected final String key;

}