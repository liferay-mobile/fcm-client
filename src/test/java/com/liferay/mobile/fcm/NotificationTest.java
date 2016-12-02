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

		String json = new Gson().toJson(notification);

		assertEquals(json, "{\"body\":\"body\",\"title\":\"title\"}");
	}

	@Test
	public void testNotificationWithClickAction() {
		Notification notification = new Notification.Builder()
			.clickAction("action")
			.build();

		String json = new Gson().toJson(notification);

		assertEquals(json, "{\"click_action\":\"action\"}");
	}

}