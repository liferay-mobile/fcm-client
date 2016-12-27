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
import com.liferay.mobile.fcm.exception.ExceededNumberOfMulticastTokens;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Bruno Farache
 */
public class MessageTest {

	@Test
	public void testMessageWithCollapseKey() {
		String collapseKey = "key";

		Message message = new Message.Builder()
			.collapseKey(collapseKey)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"collapse_key\":\"" + collapseKey + "\"" +
			"}",
			json);

		assertEquals(collapseKey, message.collapseKey());
	}

	@Test
	public void testMessageWithContentAvailable() {
		Message message = new Message.Builder()
			.contentAvailable(true)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"content_available\":true" +
			"}",
			json);

		assertTrue(message.contentAvailable());
	}

	@Test
	public void testMessageWithoutContentAvailable() {
		Message message = new Message.Builder()
			.build();

		assertFalse(message.contentAvailable());
	}

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
	public void testMulticastMessage() throws ExceededNumberOfMulticastTokens {
		String[] tokens = new String[] {"1", "2"};

		Message message = new Message.Builder()
			.multicast(tokens)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"registration_ids\":[\"1\",\"2\"]" +
			"}",
			json);

		assertArrayEquals(tokens, message.multicast());
	}

	@Test(expected = ExceededNumberOfMulticastTokens.class)
	public void testMulticastMessageWithExceededNumberOfTokens()
		throws ExceededNumberOfMulticastTokens {

		new Message.Builder()
			.multicast(new String[1001])
			.build();
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