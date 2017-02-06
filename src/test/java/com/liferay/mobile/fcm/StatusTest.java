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
import com.liferay.mobile.fcm.json.Response;

import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Bruno Farache
 */
public class StatusTest {

	@Test
	public void testDeviceGroupMessage() {
		String json = "{" +
			"\"success\": 1," +
			"\"failure\": 0" +
		"}";

		Message message = new Message.Builder()
			.to("aUniqueKey")
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(0, status.multicastId());
		assertEquals(0, status.failed().size());

		List<MessageResult> succeeded = status.succeeded();
		assertEquals(1, succeeded.size());

		MessageResult result = succeeded.get(0);

		assertNull(result.messageId());
		assertNull(result.error());
		assertNull(result.newToken());
		assertEquals("aUniqueKey", result.token());
	}

	@Test
	public void testDeviceGroupMessageWithErrors() {
		String json = "{" +
			"\"success\":1," +
			"\"failure\":2," +
			"\"failed_registration_ids\":[" +
				"\"1\"," +
				"\"2\"" +
			"]" +
		"}";

		Message message = new Message.Builder()
			.to("aUniqueKey")
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(0, status.multicastId());

		List<MessageResult> succeeded = status.succeeded();
		assertEquals(1, succeeded.size());
		assertNull(succeeded.get(0).error());

		List<MessageResult> failed = status.failed();
		assertEquals(2, failed.size());

		MessageResult failed1 = failed.get(0);
		assertEquals("1", failed1.token());
		assertNotNull(failed1.error());

		MessageResult failed2 = failed.get(1);
		assertEquals("2", failed2.token());
		assertNotNull(failed2.error());
	}

	@Test
	public void testMulticastMessage() throws Exception {
		String json = "{" +
			"\"multicast_id\": 108," +
			"\"success\": 1," +
			"\"failure\": 0," +
			"\"canonical_ids\": 0," +
			"\"results\": [" +
				"{\"message_id\": \"1:08\"}" +
			"]" +
		"}";

		Message message = new Message.Builder()
			.to("1")
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(108, status.multicastId());
		assertEquals(0, status.failed().size());

		List<MessageResult> succeeded = status.succeeded();
		assertEquals(1, succeeded.size());

		MessageResult result = succeeded.get(0);
		assertEquals("1:08", result.messageId());
		assertEquals("1", result.token());
		assertNull(result.newToken());
		assertNull(result.error());
	}

	@Test
	public void testMulticastMessageInvalidNumberOfToTokens() throws Exception {
		String json = "{" +
			"\"multicast_id\": 216," +
			"\"success\": 1," +
			"\"failure\": 0," +
			"\"canonical_ids\": 0," +
			"\"results\": [" +
				"{\"message_id\": \"1:0408\"}" +
			"]" +
		"}";

		Message message = new Message.Builder()
			.to("1", "2")
			.build();

		Response response = Json.fromJson(
			new StringReader(json), Response.class);

		Status status = new StatusFactory().createMulticastStatus(
			message, response);

		assertEquals(1, status.succeeded().size());
		assertNull(status.succeeded().get(0).token());
	}

	@Test
	public void testMulticastMessageWithErrors() throws Exception {
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

		Message message = new Message.Builder()
			.to("1", "2", "3", "4", "5", "6")
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(216, status.multicastId());

		List<MessageResult> failed = status.failed();
		assertEquals(3, failed.size());

		MessageResult failed1 = failed.get(0);
		assertEquals("Unavailable", failed1.error());
		assertEquals("2", failed1.token());

		MessageResult failed2 = failed.get(1);
		assertEquals("InvalidRegistration", failed2.error());
		assertEquals("3", failed2.token());

		MessageResult failed3 = failed.get(2);
		assertEquals("NotRegistered", failed3.error());
		assertEquals("6", failed3.token());

		List<MessageResult> succeeded = status.succeeded();
		assertEquals(3, succeeded.size());

		MessageResult succeeded1 = succeeded.get(0);
		assertEquals("1:0408", succeeded1.messageId());
		assertEquals("1", succeeded1.token());

		MessageResult succeeded2 = succeeded.get(1);
		assertEquals("1:1516", succeeded2.messageId());
		assertEquals("4", succeeded2.token());

		MessageResult succeeded3 = succeeded.get(2);
		assertEquals("1:2342", succeeded3.messageId());
		assertEquals("32", succeeded3.newToken());
		assertEquals("5", succeeded3.token());
	}

	@Test
	public void testTopicMessage() throws Exception {
		String json = "{" +
			"\"message_id\": \"1\"" +
		"}";

		Message message = new Message.Builder()
			.to(new Topic("topic"))
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(0, status.multicastId());
		assertEquals(0, status.failed().size());

		List<MessageResult> succeeded = status.succeeded();
		assertEquals(1, succeeded.size());
		assertEquals("1", succeeded.get(0).messageId());
	}

	@Test
	public void testTopicMessageWithError() throws Exception {
		String json = "{" +
			"\"error\": \"TopicsMessageRateExceeded\"" +
		"}";

		Message message = new Message.Builder()
			.to(new Topic("topic"))
			.build();

		Status status = new StatusFactory().createStatus(
			message, new StringReader(json));

		assertEquals(0, status.multicastId());
		assertEquals(0, status.succeeded().size());

		List<MessageResult> failed = status.failed();
		assertEquals(1, failed.size());

		MessageResult result = failed.get(0);

		assertEquals("TopicsMessageRateExceeded", result.error());
		assertEquals("/topics/topic", result.token());
		assertNull(result.messageId());
		assertNull(result.newToken());
	}

}