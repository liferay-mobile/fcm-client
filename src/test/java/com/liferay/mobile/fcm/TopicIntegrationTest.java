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

import com.liferay.mobile.fcm.exception.InvalidTopicName;

import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * @author Bruno Farache
 */
public class TopicIntegrationTest {

	@Test
	public void testTopic() throws InvalidTopicName {
		String news = "news";

		Notification notification = new Notification.Builder()
			.title(news)
			.build();

		Message message = new Message.Builder()
			.to(new Topic(news))
			.notification(notification)
			.build();

		sender()
			.send(message)
			.test()
			.assertNoErrors()
			.assertValue(Status::isSuccessful)
			.assertValue(status -> (200 == status.httpStatusCode()))
			.assertValue(status -> (status.multicastId() == 0))
			.assertValue(status -> (0 == status.failed().size()))
			.assertValue(status -> (1 == status.succeeded().size()))
			.assertValue(status -> {
				if (status.succeeded().get(0).messageId() != null) {
					return true;
				}

				return false;
			});
	}

	protected RxSender sender() {
		RxSender sender = new RxSender(config.key);

		if (!config.key.equals("key")) {
			return sender;
		}

		try {
			MockWebServer server = new MockWebServer();
			server.start();

			String body = "{" +
				"\"message_id\": \"1\"" +
			"}";

			server.enqueue(new MockResponse().setBody(body));

			String url = server.url("/fcm/send").toString();
			sender = new RxSender(new Sender(config.key, url));
		}
		catch (IOException ioe) {
		}

		return sender;
	}

	private final Config config = new Config();

}