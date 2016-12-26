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

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Farache
 */
public class IntegrationTest {

	@Test
	public void testSendMessage() throws Exception {
		RxSender sender = new RxSender(config.key);
		assertEquals(config.key, sender.sender().key());

		Map<String, String> data = new HashMap<>();
		data.put("foo", "bar");

		Message message = new Message.Builder()
			.to(config.token)
			.data(data)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"data\":{\"foo\":\"bar\"}," +
				"\"to\":\"" + config.token+ "\"" +
			"}",
			json);

		assertEquals(config.token, message.to());
		assertEquals(data, message.data());

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	@Test
	public void testSendNotification() throws Exception {
		RxSender sender = new RxSender(config.key);

		Notification notification = new Notification.Builder()
			.title("foo")
			.body("bar")
			.build();

		Message message = new Message.Builder()
			.to(config.token)
			.notification(notification)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"notification\":{\"body\":\"bar\",\"title\":\"foo\"}," +
				"\"to\":\"" + config.token + "\"" +
			"}",
			json);

		assertEquals(config.token, message.to());
		assertEquals(notification, message.notification());

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	@Test
	public void testSendNotificationToTopic() throws Exception {
		RxSender sender = new RxSender(config.key);

		String news = "news";

		Notification notification = new Notification.Builder()
			.title(news)
			.build();

		Message message = new Message.Builder()
			.to(new Topic(news))
			.notification(notification)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"notification\":{\"title\":\"news\"}," +
				"\"to\":\"/topics/" + news + "\"" +
			"}",
			json);

		assertEquals("/topics/" + news, message.to());
		assertEquals(notification, message.notification());

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	@Test
	public void testSendNotificationWithLocalizedTitleAndBody()
		throws Exception {

		RxSender sender = new RxSender(config.key);

		Notification notification = new Notification.Builder()
			.titleLocalizationKey("localized_title")
			.titleLocalizationArguments("foo")
			.bodyLocalizationKey("localized_body")
			.bodyLocalizationArguments("bar")
			.build();

		Message message = new Message.Builder()
			.to(config.token)
			.notification(notification)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"notification\":{" +
					"\"body_loc_key\":\"localized_body\"," +
					"\"body_loc_args\":[\"bar\"]," +
					"\"title_loc_key\":\"localized_title\"," +
					"\"title_loc_args\":[\"foo\"]" +
				"}," +
				"\"to\":\"" + config.token + "\"" +
			"}",
			json);

		assertEquals(config.token, message.to());
		assertEquals(notification, message.notification());

		sender
			.send(message)
			.test()
			.assertValue(Response::isSuccessful)
			.assertValue(response -> (200 == response.code()))
			.assertNoErrors();
	}

	private final Config config = new Config();

}