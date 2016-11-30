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
public class Message {

	public Object data() {
		return data;
	}

	public String to() {
		return to;
	}

	public static class Builder {

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public Builder to(String to) {
			this.to = to;
			return this;
		}

		public Message build() {
			return new Message(this);
		}

		protected Object data;
		protected String to;

	}

	protected Message(Builder builder) {
		this.data = builder.data;
		this.to = builder.to;
	}

	protected final Object data;
	protected final String to;

}