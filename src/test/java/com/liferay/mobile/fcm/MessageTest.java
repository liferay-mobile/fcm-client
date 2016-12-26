/*
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

import com.liferay.mobile.fcm.Message.Priority;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Farache
 */
public class MessageTest {

	@Test
	public void testMessageWithData() {
		String token = "token";

		Map<String, String> data = new HashMap<>();
		data.put("foo", "bar");

		Message message = new Message.Builder()
			.to(token)
			.data(data)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"data\":{\"foo\":\"bar\"}," +
				"\"to\":\"" + token + "\"" +
			"}",
			json);

		assertEquals(token, message.to());
		assertEquals(data, message.data());
	}

	@Test
	public void testPriorityHigh() {
		Message message = new Message.Builder()
			.priority(Priority.HIGH)
			.build();

		String json = Sender.toJson(message);

		assertEquals(Priority.HIGH, Priority.valueOf("HIGH"));
		assertEquals(Priority.HIGH.ordinal(), 1);
		assertEquals("high", Priority.HIGH.toString());
		assertEquals(Priority.HIGH, message.priority());
		assertEquals(json, "{\"priority\":\"high\"}");
	}

	@Test
	public void testPriorityNormal() {
		Message message = new Message.Builder()
			.priority(Priority.NORMAL)
			.build();

		String json = Sender.toJson(message);

		assertEquals(Priority.NORMAL, Priority.valueOf("NORMAL"));
		assertEquals(Priority.NORMAL.ordinal(), 0);
		assertEquals("normal", Priority.NORMAL.toString());
		assertEquals(Priority.NORMAL, message.priority());
		assertEquals(json, "{\"priority\":\"normal\"}");
	}

}