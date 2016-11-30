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

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import okio.Buffer;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Farache
 */
public class SenderTest {

	@Test
	public void testRequestBody() throws IOException {
		Sender sender = new Sender(config.key);
		Message message = createMessage(createData());
		Request request = sender.createRequest(message);

		RequestBody body = request.body();

		Buffer buffer = new Buffer();
		body.writeTo(buffer);
		String content = buffer.readUtf8();

		String json = new Gson().toJson(message);

		assertEquals(json, content);
	}

	@Test
	public void testRequestHeaders() {
		Sender sender = new Sender(config.key);
		Request request = sender.createRequest(createMessage(createData()));
		String body = request.body().contentType().toString();

		assertEquals("key=" + config.key, request.header(Sender.AUTHORIZATION));
		assertEquals("application/json; charset=utf-8", body);
	}

	@Test
	public void testRequestURL() {
		Sender sender = new Sender(config.key);
		Request request = sender.createRequest(createMessage(createData()));
		assertEquals(Sender.URL, request.url().toString());
	}

	@Test
	public void testResponseStatus() {
		Sender sender = new Sender(config.key);
		sender
			.send(createMessage(createData()))
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	private Map createData() {
		Map<String, String> data = new HashMap<>();
		data.put("foo", "bar");
		return data;
	}

	private Message createMessage(Map data) {
		return new Message.Builder()
			.to(config.token)
			.data(data)
			.build();
	}

	private final Config config = new Config();

}