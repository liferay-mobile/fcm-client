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

/**
 * @author Bruno Farache
 */
public class Result {

	public String error() {
		return error;
	}

	public String messageId() {
		return messageId;
	}

	public String newToken() {
		return newToken;
	}

	protected String error;
	@SerializedName("message_id")
	protected String messageId;
	@SerializedName("registration_id")
	protected String newToken;

}