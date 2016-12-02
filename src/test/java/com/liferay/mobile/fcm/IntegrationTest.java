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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * @author Bruno Farache
 */
public class IntegrationTest {

	@Test
	public void testSendMessage() {
		Sender sender = new Sender(config.key);

		Map<String, String> data = new HashMap<>();
		data.put("foo", "bar");

		Message message = new Message.Builder()
			.to(config.token)
			.data(data)
			.build();

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	@Test
	public void testSendNotification() {
		Sender sender = new Sender(config.key);

		Notification notification = new Notification.Builder()
			.title("foo")
			.body("bar")
			.build();

		Message message = new Message.Builder()
			.to(config.token)
			.notification(notification)
			.build();

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	private final Config config = new Config();

}