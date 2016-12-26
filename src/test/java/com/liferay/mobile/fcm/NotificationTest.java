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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Farache
 */
public class NotificationTest {

	@Test
	public void testNotificationWithTitleAndBody() {
		Notification notification = new Notification.Builder()
			.title("title")
			.body("body")
			.build();

		String json = Sender.toJson(notification);

		assertEquals(json, "{\"body\":\"body\",\"title\":\"title\"}");
	}

	@Test
	public void testNotificationWithAllProperties() {
		String badge = "badge";
		String body = "body";
		String bodyLocalizationKey = "bodyLocalizationKey";
		String[] bodyLocalizationArguments = {"foo", "bar"};
		String action = "action";
		String color = "#000000";
		String icon = "icon";
		String sound = "sound";
		String tag = "tag";
		String title = "title";
		String titleLocalizationKey = "titleLocalizationKey";
		String[] titleLocalizationArguments = {"bar", "foo"};

		Notification notification = new Notification.Builder()
			.badge(badge)
			.body(body)
			.bodyLocalizationKey(bodyLocalizationKey)
			.bodyLocalizationArguments(bodyLocalizationArguments)
			.clickAction(action)
			.color(color)
			.icon(icon)
			.sound(sound)
			.tag(tag)
			.title(title)
			.titleLocalizationKey(titleLocalizationKey)
			.titleLocalizationArguments(titleLocalizationArguments)
			.build();

		assertEquals(badge, notification.badge());
		assertEquals(body, notification.body());
		assertEquals(bodyLocalizationKey, notification.bodyLocalizationKey());
		assertArrayEquals(
			bodyLocalizationArguments,
			notification.bodyLocalizationArguments());
		assertEquals(action, notification.clickAction());
		assertEquals(color, notification.color());
		assertEquals(icon, notification.icon());
		assertEquals(sound, notification.sound());
		assertEquals(tag, notification.tag());
		assertEquals(title, notification.title());
		assertEquals(
				titleLocalizationKey, notification.titleLocalizationKey());
		assertArrayEquals(
			titleLocalizationArguments,
			notification.titleLocalizationArguments());

		String json = Sender.toJson(notification);

		assertEquals(json,
		"{" +
			"\"badge\":\"badge\"," +
			"\"body\":\"body\"," +
			"\"body_loc_key\":\"bodyLocalizationKey\"," +
			"\"body_loc_args\":[\"foo\",\"bar\"]," +
			"\"click_action\":\"action\"," +
			"\"color\":\"#000000\"," +
			"\"icon\":\"icon\"," +
			"\"sound\":\"sound\"," +
			"\"tag\":\"tag\"," +
			"\"title\":\"title\"," +
			"\"title_loc_key\":\"titleLocalizationKey\"," +
			"\"title_loc_args\":[\"bar\",\"foo\"]" +
		"}");
	}

	@Test
	public void testNotificationWithClickAction() {
		Notification notification = new Notification.Builder()
			.clickAction("action")
			.build();

		String json = Sender.toJson(notification);

		assertEquals(json, "{\"click_action\":\"action\"}");
	}

	@Test
	public void testMessageWithNotification() {
		String token = "token";

		Notification notification = new Notification.Builder()
			.title("foo")
			.body("bar")
			.build();

		Message message = new Message.Builder()
			.to(token)
			.notification(notification)
			.build();

		String json = Sender.toJson(message);

		assertEquals(
			"{" +
				"\"notification\":{\"body\":\"bar\",\"title\":\"foo\"}," +
				"\"to\":\"" + token + "\"" +
			"}",
			json);

		assertEquals(token, message.to());
		assertEquals(notification, message.notification());
	}

	@Test
	public void testMessageWithLocalizedNotification() {
		String token = "token";

		Notification notification = new Notification.Builder()
			.titleLocalizationKey("localized_title")
			.titleLocalizationArguments("foo")
			.bodyLocalizationKey("localized_body")
			.bodyLocalizationArguments("bar")
			.build();

		Message message = new Message.Builder()
			.to(token)
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
				"\"to\":\"" + token + "\"" +
			"}",
			json);

		assertEquals(token, message.to());
		assertEquals(notification, message.notification());
	}

}