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
import com.liferay.mobile.fcm.exception.IllegalNumberOfTokens;
import com.liferay.mobile.fcm.exception.ExceededTimeToLive;
import com.liferay.mobile.fcm.json.Json;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

		String json = Json.toJson(message);

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

		String json = Json.toJson(message);

		assertEquals(
			"{" +
				"\"content_available\":true" +
			"}",
			json);

		assertTrue(message.contentAvailable());
	}

	@Test
	public void testMessageWithoutContentAvailable() {
		Message message = new Message.Builder().build();
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

		String json = Json.toJson(message);

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
	public void testMessageWithDryRun() {
		Message message = new Message.Builder()
			.dryRun(true)
			.build();

		String json = Json.toJson(message);

		assertEquals(
			"{" +
				"\"dry_run\":true" +
			"}",
			json);

		assertTrue(message.dryRun());
	}

	@Test
	public void testMessageWithoutDryRun() {
		Message message = new Message.Builder().build();
		assertFalse(message.dryRun());
	}

	@Test
	public void testMessageWithRestrictedPackageName() {
		String packageName = "packageName";

		Message message = new Message.Builder()
			.restrictedPackageName(packageName)
			.build();

		String json = Json.toJson(message);
		assertEquals(
			"{" +
				"\"restricted_package_name\":\"" + packageName + "\"" +
			"}",
			json);

		assertEquals(packageName, message.restrictedPackageName());
	}

	@Test
	public void testMessageWithTimeToLive() throws ExceededTimeToLive {
		Message message = new Message.Builder()
			.timeToLive(1, TimeUnit.SECONDS)
			.build();

		String json = Json.toJson(message);

		assertEquals(
			"{" +
				"\"time_to_live\":1" +
			"}",
			json);

		assertEquals(1, message.timeToLive());
	}

	@Test
	public void testMessageWithTimeToLiveWithOneHour()
		throws ExceededTimeToLive {

		Message message = new Message.Builder()
			.timeToLive(1, TimeUnit.HOURS)
			.build();

		String json = Json.toJson(message);

		int secondsInOneHour = 60 * 60;

		assertEquals(
			"{" +
				"\"time_to_live\":" + secondsInOneHour +
			"}",
			json);

		assertEquals(secondsInOneHour, message.timeToLive());
	}

	@Test(expected = ExceededTimeToLive.class)
	public void testMulticastMessageWithExceededTimeToLive()
		throws ExceededTimeToLive {

		new Message.Builder()
			.timeToLive(2419201, TimeUnit.SECONDS)
			.build();
	}

	@Test
	public void testMessageWithoutTimeToLive() {
		Message message = new Message.Builder().build();
		assertEquals(0, message.timeToLive());
	}

	@Test
	public void testMulticastMessage() {
		String[] tokens = new String[] {"1", "2"};

		Message message = new Message.Builder()
			.to(tokens)
			.build();

		String json = Json.toJson(message);

		assertEquals(
			"{" +
				"\"registration_ids\":[\"1\",\"2\"]" +
			"}",
			json);

		assertArrayEquals(tokens, message.multicast().toArray());
	}

	@Test(expected = IllegalNumberOfTokens.class)
	public void testMulticastMessageWithExceededNumberOfTokens() {
		new Message.Builder()
			.to(new String[1001])
			.build();
	}

	@Test(expected = IllegalNumberOfTokens.class)
	public void testMulticastMessageWithZeroTokens() {
		new Message.Builder()
			.to(new String[]{})
			.build();
	}

	@Test
	public void testPriorityHigh() {
		Message message = new Message.Builder()
			.priority(Priority.HIGH)
			.build();

		String json = Json.toJson(message);

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

		String json = Json.toJson(message);

		assertEquals(Priority.NORMAL, Priority.valueOf("NORMAL"));
		assertEquals(Priority.NORMAL.ordinal(), 0);
		assertEquals("normal", Priority.NORMAL.toString());
		assertEquals(Priority.NORMAL, message.priority());
		assertEquals(json, "{\"priority\":\"normal\"}");
	}

}