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

import com.liferay.mobile.fcm.exception.ExceededNumberOfMulticastTokens;
import com.liferay.mobile.fcm.exception.ExceededTimeToLive;

import java.util.concurrent.TimeUnit;

import com.google.gson.annotations.SerializedName;

/**
 * @author Bruno Farache
 */
public class Message {

	public String collapseKey() {
		return collapseKey;
	}

	public String condition() {
		return condition;
	}

	public boolean contentAvailable() {
		if (contentAvailable == null) {
			return false;
		}

		return contentAvailable;
	}

	public Object data() {
		return data;
	}

	public boolean dryRun() {
		if (dryRun == null) {
			return false;
		}

		return dryRun;
	}

	public String[] multicast() {
		return multicast;
	}

	public Notification notification() {
		return notification;
	}

	public Priority priority() {
		return priority;
	}

	public String restrictedPackageName() {
		return restrictedPackageName;
	}

	public String to() {
		return to;
	}

	public int timeToLive() {
		if (timeToLive == null) {
			return 0;
		}

		return timeToLive;
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

		public Builder collapseKey(String collapseKey) {
			this.collapseKey = collapseKey;
			return this;
		}

		public Builder condition(Condition condition) {
			return condition(condition.condition());
		}

		public Builder condition(String condition) {
			this.condition = condition;
			return this;
		}

		public Builder contentAvailable(boolean contentAvailable) {
			this.contentAvailable = contentAvailable;
			return this;
		}

		public Builder data(Object data) {
			this.data = data;
			return this;
		}

		public Builder dryRun(boolean dryRun) {
			this.dryRun = dryRun;
			return this;
		}

		public Builder multicast(String... tokens)
			throws ExceededNumberOfMulticastTokens {

			if (tokens.length > 1000) {
				throw new ExceededNumberOfMulticastTokens(tokens);
			}

			this.multicast = tokens;
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

		public Builder restrictedPackageName(String restrictedPackageName) {
			this.restrictedPackageName = restrictedPackageName;
			return this;
		}

		public Builder to(Topic topic) {
			return to(topic.path());
		}

		public Builder to(String token) {
			this.to = token;
			return this;
		}

		public Builder timeToLive(int timeToLive, TimeUnit unit)
			throws ExceededTimeToLive {

			long duration = unit.toSeconds(timeToLive);
			long max = TimeUnit.DAYS.toSeconds(28);

			if (duration > max) {
				throw new ExceededTimeToLive(duration);
			}

			this.timeToLive = (int)duration;
			return this;
		}

		public Message build() {
			return new Message(this);
		}

		protected String collapseKey;
		protected String condition;
		protected Boolean contentAvailable;
		protected Object data;
		protected Boolean dryRun;
		protected String[] multicast;
		protected Notification notification;
		protected Priority priority;
		protected String restrictedPackageName;
		protected Integer timeToLive;
		protected String to;

	}

	protected Message(Builder builder) {
		this.collapseKey = builder.collapseKey;
		this.condition = builder.condition;
		this.contentAvailable = builder.contentAvailable;
		this.data = builder.data;
		this.dryRun = builder.dryRun;
		this.multicast = builder.multicast;
		this.notification = builder.notification;
		this.priority = builder.priority;
		this.restrictedPackageName = builder.restrictedPackageName;
		this.to = builder.to;
		this.timeToLive = builder.timeToLive;
	}

	@SerializedName("collapse_key")
	protected final String collapseKey;
	protected final String condition;
	@SerializedName("content_available")
	protected final Boolean contentAvailable;
	protected final Object data;
	@SerializedName("dry_run")
	protected final Boolean dryRun;
	@SerializedName("registration_ids")
	protected final String[] multicast;
	protected final Notification notification;
	protected final Priority priority;
	@SerializedName("restricted_package_name")
	protected final String restrictedPackageName;
	@SerializedName("time_to_live")
	protected final Integer timeToLive;
	protected final String to;

}