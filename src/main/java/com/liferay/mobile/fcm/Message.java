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

import com.google.gson.annotations.SerializedName;

/**
 * @author Bruno Farache
 */
public class Message {

	public String condition() {
		return condition;
	}

	public Object data() {
		return data;
	}

	public Notification notification() {
		return notification;
	}

	public Priority priority() {
		return priority;
	}

	public String to() {
		return to;
	}

	public enum Priority {

		@SerializedName("normal")
		NORMAL,

		@SerializedName("high")
		HIGH;

		@Override
		public String toString() {
			return name().toLowerCase();
		}

	}

	public static class Builder {

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public Builder notification(Notification notification) {
			this.notification = notification;
			return this;
		}

		public Builder priority(Priority priority) {
			this.priority = priority;
			return this;
		}

		public Builder to(Topic topic) {
			return to(topic.path());
		}

		public Builder to(String to) {
			this.to = to;
			return this;
		}

		public Builder condition(Condition condition) {
			return condition(condition.condition());
		}

		public Builder condition(String condition) {
			this.condition = condition;
			return this;
		}

		public Message build() {
			return new Message(this);
		}

		protected String condition;
		protected Object data;
		protected Notification notification;
		protected Priority priority;
		protected String to;

	}

	protected Message(Builder builder) {
		this.condition = builder.condition;
		this.data = builder.data;
		this.notification = builder.notification;
		this.priority = builder.priority;
		this.to = builder.to;
	}

	protected final String condition;
	protected final Object data;
	protected final Notification notification;
	protected final Priority priority;
	protected final String to;

}