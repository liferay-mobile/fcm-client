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
public class Notification {

	public String badge() {
		return badge;
	}

	public String body() {
		return body;
	}

	public String clickAction() {
		return clickAction;
	}

	public String color() {
		return color;
	}

	public String icon() {
		return icon;
	}

	public String sound() {
		return sound;
	}

	public String tag() {
		return tag;
	}

	public String title() {
		return title;
	}

	public static class Builder {

		public Builder badge(String badge) {
			this.badge = badge;
			return this;
		}

		public Builder body(String body) {
			this.body = body;
			return this;
		}

		public Builder clickAction(String clickAction) {
			this.clickAction = clickAction;
			return this;
		}

		public Builder color(String color) {
			this.color = color;
			return this;
		}

		public Builder icon(String icon) {
			this.icon = icon;
			return this;
		}

		public Builder sound(String sound) {
			this.sound = sound;
			return this;
		}

		public Builder tag(String tag) {
			this.tag = tag;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Notification build() {
			return new Notification(this);
		}

		protected String badge;
		protected String body;
		protected String clickAction;
		protected String color;
		protected String icon;
		protected String sound;
		protected String tag;
		protected String title;

	}

	protected Notification(Builder builder) {
		this.badge = builder.badge;
		this.body = builder.body;
		this.clickAction = builder.clickAction;
		this.color = builder.color;
		this.icon = builder.icon;
		this.sound = builder.sound;
		this.tag = builder.tag;
		this.title = builder.title;
	}

	protected final String badge;
	protected final String body;
	@SerializedName("click_action")
	protected final String clickAction;
	protected final String color;
	protected final String icon;
	protected final String sound;
	protected final String tag;
	protected final String title;

}