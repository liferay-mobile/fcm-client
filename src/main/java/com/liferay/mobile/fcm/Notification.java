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

	public String bodyLocalizationKey() {
		return bodyLocalizationKey;
	}

	public String[] bodyLocalizationArguments() {
		return bodyLocalizationArguments;
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

	public String titleLocalizationKey() {
		return titleLocalizationKey;
	}

	public String[] titleLocalizationArguments() {
		return titleLocalizationArguments;
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

		public Builder bodyLocalizationKey(String bodyLocalizationKey) {
			this.bodyLocalizationKey = bodyLocalizationKey;
			return this;
		}

		public Builder bodyLocalizationArguments(
			String... bodyLocalizationArguments) {

			this.bodyLocalizationArguments = bodyLocalizationArguments;
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

		public Builder titleLocalizationKey(String titleLocalizationKey) {
			this.titleLocalizationKey = titleLocalizationKey;
			return this;
		}

		public Builder titleLocalizationArguments(
			String... titleLocalizationArguments) {

			this.titleLocalizationArguments = titleLocalizationArguments;
			return this;
		}

		public Notification build() {
			return new Notification(this);
		}

		String badge;
		String body;
		String bodyLocalizationKey;
		String[] bodyLocalizationArguments;
		String clickAction;
		String color;
		String icon;
		String sound;
		String tag;
		String title;
		String titleLocalizationKey;
		String[] titleLocalizationArguments;

	}

	protected Notification(Builder builder) {
		this.badge = builder.badge;
		this.body = builder.body;
		this.bodyLocalizationKey = builder.bodyLocalizationKey;
		this.bodyLocalizationArguments = builder.bodyLocalizationArguments;
		this.clickAction = builder.clickAction;
		this.color = builder.color;
		this.icon = builder.icon;
		this.sound = builder.sound;
		this.tag = builder.tag;
		this.title = builder.title;
		this.titleLocalizationKey = builder.titleLocalizationKey;
		this.titleLocalizationArguments = builder.titleLocalizationArguments;
	}

	protected final String badge;
	protected final String body;
	@SerializedName("body_loc_key")
	protected final String bodyLocalizationKey;
	@SerializedName("body_loc_args")
	protected final String[] bodyLocalizationArguments;
	@SerializedName("click_action")
	protected final String clickAction;
	protected final String color;
	protected final String icon;
	protected final String sound;
	protected final String tag;
	protected final String title;
	@SerializedName("title_loc_key")
	protected final String titleLocalizationKey;
	@SerializedName("title_loc_args")
	protected final String[] titleLocalizationArguments;

}