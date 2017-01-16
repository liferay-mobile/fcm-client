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

package com.liferay.mobile.fcm.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;

/**
 * @author Bruno Farache
 */
public abstract class Json {

	public static <T> T fromJson(Reader reader, Class<T> clazz) {
		return gson().fromJson(reader, clazz);
	}

	public static String toJson(Object object) {
		return gson().toJson(object);
	}

	protected static GsonBuilder createDefaultGsonBuilder() {
		return new GsonBuilder().disableHtmlEscaping();
	}

	protected static Gson gson() {
		if (gson == null) {
			gson = createDefaultGsonBuilder()
				.create();
		}

		return gson;
	}

	protected static Gson gson;

}