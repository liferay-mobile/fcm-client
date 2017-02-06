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
				MessageResult.Builder messageResultBuilder =
					new MessageResult.Builder()
						.token(failedRegistrationsId)
						.error("failed");

				builder.addFailure(messageResultBuilder.build());
			}
		}

		return builder.build();
	}

	protected Status createMulticastStatus(Message message, Response response) {
		Status.Builder builder = new Status.Builder();
		List<Result> results = response.results();

		for (int i = 0; i < results.size(); i++) {
			Result result = results.get(i);
			List<String> multicast = message.multicast();
			String token = message.to();

			if ((multicast != null) && (multicast.size() == results.size())) {
				token = multicast.get(i);
			}

			MessageResult.Builder messageResultBuilder =
				new MessageResult.Builder().token(token);

			if (result.error() == null) {
				messageResultBuilder.messageId(result.messageId());

				if (result.newToken() != null) {
					messageResultBuilder.newToken(result.newToken());
				}

				builder.addSuccess(messageResultBuilder.build());
			}
			else {
				messageResultBuilder.error(result.error());
				builder.addFailure(messageResultBuilder.build());
			}
		}

		return builder.multicastId(response.multicastId()).build();
	}

	protected Status createTopicStatus(Message message, Response response) {
		Status.Builder builder = new Status.Builder();

		MessageResult.Builder messageResultBuilder = new MessageResult.Builder()
			.token(message.to());

		if (response.error() != null) {
			messageResultBuilder.error(response.error());
			builder.addFailure(messageResultBuilder.build());
		}
		else {
			messageResultBuilder.messageId(response.messageId());
			builder.addSuccess(messageResultBuilder.build());
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