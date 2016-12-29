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

import com.liferay.mobile.fcm.json.Json;

import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Bruno Farache
 */
public class ResponseTest {

	@Test
	public void testResponse() {
		String json = "{" +
			"\"multicast_id\": 108," +
			"\"success\": 1," +
			"\"failure\": 0," +
			"\"canonical_ids\": 0," +
			"\"results\": [" +
				"{\"message_id\": \"1:08\"}" +
			"]" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(108, response.multicastId());
		assertEquals(1, response.numberOfSucceededMessages());
		assertEquals(0, response.numberOfFailedMessages());
		assertEquals(0, response.numberOfNewTokens());

		List<Result> results = response.results();
		assertEquals(1, results.size());
		assertEquals("1:08", results.get(0).messageId());
	}

	@Test
	public void testResponseWithErrors() {
		String json = "{" +
			"\"multicast_id\": 216," +
			"\"success\": 3," +
			"\"failure\": 3," +
			"\"canonical_ids\": 1," +
			"\"results\": [" +
				"{\"message_id\": \"1:0408\"}," +
				"{\"error\": \"Unavailable\"}," +
				"{\"error\": \"InvalidRegistration\"}," +
				"{\"message_id\": \"1:1516\"}," +
				"{\"message_id\": \"1:2342\", \"registration_id\": \"32\"}," +
				"{ \"error\": \"NotRegistered\"}" +
			"]" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(216, response.multicastId());
		assertEquals(3, response.numberOfSucceededMessages());
		assertEquals(3, response.numberOfFailedMessages());
		assertEquals(1, response.numberOfNewTokens());

		List<Result> results = response.results();
		assertEquals(6, results.size());

		assertEquals("1:0408", results.get(0).messageId());
		assertEquals("Unavailable", results.get(1).error());
		assertEquals("InvalidRegistration", results.get(2).error());
		assertEquals("1:1516", results.get(3).messageId());
		assertEquals("1:2342", results.get(4).messageId());
		assertEquals("32", results.get(4).newToken());
		assertEquals("NotRegistered", results.get(5).error());
	}

	@Test
	public void testTopicResponseFailed() {
		String json = "{" +
			"\"error\": \"TopicsMessageRateExceeded\"" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(0, response.multicastId());
		assertEquals(1, response.numberOfFailedMessages());
		assertEquals(0, response.numberOfNewTokens());
		assertEquals(0, response.numberOfSucceededMessages());

		List<Result> results = response.results();
		assertEquals(1, results.size());

		Result failed = results.get(0);

		assertEquals("TopicsMessageRateExceeded", failed.error());
		assertNull(failed.messageId());
		assertNull(failed.newToken());
	}

	@Test
	public void testTopicResponseSucceeded() {
		String json = "{" +
			"\"message_id\": \"1\"" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(0, response.multicastId());
		assertEquals(0, response.numberOfFailedMessages());
		assertEquals(0, response.numberOfNewTokens());
		assertEquals(1, response.numberOfSucceededMessages());

		List<Result> results = response.results();
		assertEquals(1, results.size());
		assertEquals("1", results.get(0).messageId());
	}

	@Test
	public void testDeviceGroupResponseFailed() {
		String json = "{" +
			"\"success\":1," +
			"\"failure\":2," +
			"\"failed_registration_ids\":[" +
				"\"1\"," +
				"\"2\"" +
			"]" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(0, response.multicastId());
		assertEquals(2, response.numberOfFailedMessages());
		assertEquals(0, response.numberOfNewTokens());
		assertEquals(1, response.numberOfSucceededMessages());

		List<Result> results = response.results();
		assertEquals(3, results.size());

		Result succeeded = results.get(0);
		assertNotNull(succeeded);
		assertNull(succeeded.error());

		Result failed1 = results.get(1);
		assertEquals("1", failed1.token());
		assertNotNull(failed1.error());

		Result failed2 = results.get(2);
		assertEquals("2", failed2.token());
		assertNotNull(failed2.error());
	}

	@Test
	public void testDeviceGroupResponseSucceeded() {
		String json = "{" +
			"\"success\": 1," +
			"\"failure\": 0" +
		"}";

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		assertEquals(0, response.multicastId());
		assertEquals(0, response.numberOfFailedMessages());
		assertEquals(0, response.numberOfNewTokens());
		assertEquals(1, response.numberOfSucceededMessages());

		List<Result> results = response.results();
		assertEquals(1, results.size());

		Result succeeded = results.get(0);

		assertNotNull(succeeded);
		assertNull(succeeded.messageId());
		assertNull(succeeded.error());
		assertNull(succeeded.newToken());
	}

}