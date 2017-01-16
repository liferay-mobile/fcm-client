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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class Status {

	public List<MessageResult> failed() {
		return failed;
	}

	public okhttp3.Response httpResponse() {
		return httpResponse;
	}

	public int httpStatusCode() {
		return httpResponse().code();
	}

	public boolean isSuccessful() {
		return httpResponse().isSuccessful();
	}

	public long multicastId() {
		return multicastId;
	}

	public List<MessageResult> succeeded() {
		return succeeded;
	}

	protected void httpResponse(okhttp3.Response httpResponse) {
		this.httpResponse = httpResponse;
	}

	public static class Builder {

		public Builder addFailure(MessageResult failure) {
			this.failed.add(failure);
			return this;
		}

		public Builder addSuccess(MessageResult success) {
			this.succeeded.add(success);
			return this;
		}

		public Builder multicastId(long multicastId) {
			this.multicastId = multicastId;
			return this;
		}

		public Status build() {
			return new Status(this);
		}

		final List<MessageResult> failed = new ArrayList<>();
		long multicastId;
		final List<MessageResult> succeeded = new ArrayList<>();
	}

	protected Status(Builder builder) {
		this.failed = builder.failed;
		this.multicastId = builder.multicastId;
		this.succeeded = builder.succeeded;
	}

	protected final List<MessageResult> failed;
	protected okhttp3.Response httpResponse;
	protected final long multicastId;
	protected final List<MessageResult> succeeded;

}