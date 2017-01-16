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

/**
 * @author Bruno Farache
 */
public class MessageResult {

	public String error() {
		return error;
	}

	public String messageId() {
		return messageId;
	}

	public String newToken() {
		return newToken;
	}

	public String token() {
		return token;
	}

	public static class Builder {

		public Builder error(String error) {
			this.error = error;
			return this;
		}

		public Builder messageId(String messageId) {
			this.messageId = messageId;
			return this;
		}

		public Builder newToken(String newToken) {
			this.newToken = newToken;
			return this;
		}

		public Builder token(String token) {
			this.token = token;
			return this;
		}

		public MessageResult build() {
			return new MessageResult(this);
		}

		String error;
		String messageId;
		String newToken;
		String token;

	}

	protected MessageResult(Builder builder) {
		this.error = builder.error;
		this.messageId = builder.messageId;
		this.newToken = builder.newToken;
		this.token = builder.token;
	}

	protected final String error;
	protected final String messageId;
	protected final String newToken;
	protected final String token;

}