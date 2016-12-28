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

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Bruno Farache
 */
public class Response {

	public okhttp3.Response httpResponse() {
		return httpResponse;
	}

	public Response httpResponse(okhttp3.Response httpResponse) {
		this.httpResponse = httpResponse;
		return this;
	}

	public boolean isSuccessful() {
		return httpResponse().isSuccessful();
	}

	public long multicastId() {
		return multicastId;
	}

	public int numberOfFailedMessages() {
		return numberOfFailedMessages;
	}

	public int numberOfNewTokens() {
		return numberOfNewTokens;
	}

	public int numberOfSucceededMessages() {
		return numberOfSucceededMessages;
	}

	public List<Result> results() {
		return results;
	}

	public int statusCode() {
		return httpResponse().code();
	}

	protected okhttp3.Response httpResponse;

	@SerializedName("multicast_id")
	protected long multicastId;
	@SerializedName("failure")
	protected int numberOfFailedMessages;
	@SerializedName("canonical_ids")
	protected int numberOfNewTokens;
	@SerializedName("success")
	protected int numberOfSucceededMessages;
	protected List<Result> results;

}