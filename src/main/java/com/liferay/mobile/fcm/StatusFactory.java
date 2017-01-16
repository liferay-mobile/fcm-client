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

import com.liferay.mobile.fcm.json.Json;
import com.liferay.mobile.fcm.json.Response;
import com.liferay.mobile.fcm.json.Result;

import java.io.Reader;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class StatusFactory {

	public Status createStatus(Message message, Reader json) {
		Response response = Json.fromJson(json, Response.class);

		if (isTopicResponse(response)) {
			return createTopicStatus(message, response);
		}
		else if (isDeviceGroupResponse(response)) {
			return createDeviceGroupStatus(message, response);
		}

		return createMulticastStatus(message, response);
	}

	protected Status createDeviceGroupStatus(
		Message message, Response response) {

		Status.Builder builder = new Status.Builder();
		int succeededMessages = response.numberOfSucceededMessages();

		for (int i = 0; i < succeededMessages; i++) {
			MessageResult.Builder messageResult = new MessageResult.Builder()
				.token(message.to());

			builder.addSuccess(messageResult.build());
		}

		String[] failedRegistrationsIds = response.failedRegistrationIds();

		if (failedRegistrationsIds != null) {
			for (String failedRegistrationsId : failedRegistrationsIds) {
				MessageResult.Builder messageResult =
					new MessageResult.Builder()
						.token(failedRegistrationsId)
						.error("failed");

				builder.addFailure(messageResult.build());
			}
		}

		return builder.build();
	}

	protected Status createMulticastStatus(Message message, Response response) {
		Status.Builder builder = new Status.Builder();
		List<Result> results = response.results();

		for (int i = 0; i < results.size(); i++) {
			Result result = results.get(i);

			if (result.error() == null) {
				MessageResult.Builder messageResult =
					new MessageResult.Builder()
						.messageId(result.messageId())
						.token(message.multicast()[i]);

				if (result.newToken() != null) {
					messageResult.newToken(result.newToken());
				}

				builder.addSuccess(messageResult.build());
			}
			else {
				MessageResult.Builder messageResult =
					new MessageResult.Builder()
						.error(result.error())
						.token(message.multicast()[i]);

				builder.addFailure(messageResult.build());
			}
		}

		return builder.multicastId(response.multicastId()).build();
	}

	protected Status createTopicStatus(Message message, Response response) {
		Status.Builder builder = new Status.Builder();

		if (response.error() != null) {
			MessageResult messageResult = new MessageResult.Builder()
				.token(message.to())
				.error(response.error())
				.build();

			builder.addFailure(messageResult);
		}
		else {
			MessageResult messageResult = new MessageResult.Builder()
				.token(message.to())
				.messageId(response.messageId())
				.build();

			builder.addSuccess(messageResult);
		}

		return builder.build();
	}

	protected boolean isDeviceGroupResponse(Response response) {
		if (response.failedRegistrationIds() != null) {
			return true;
		}

		return (response.multicastId() == 0);
	}

	protected boolean isTopicResponse(Response response) {
		return ((response.messageId() != null) || (response.error() != null));
	}

}