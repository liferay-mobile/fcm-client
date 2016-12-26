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

import io.reactivex.Single;

import okhttp3.Response;

/**
 * @author Bruno Farache
 */

public class RxSender {

	public RxSender(String key) throws RuntimeException {
		this(new Sender(key));
	}

	protected RxSender(Sender sender) throws RuntimeException {
		try {
			Class.forName("io.reactivex.Single");
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(
				"RxSender needs RxJava 2.0.x added as runtime dependency", e);
		}

		this.sender = sender;
	}

	public Single<Response> send(Message message) {
		return Single.fromCallable(() ->
			sender.send(message)
		);
	}

	public Sender sender() {
		return sender;
	}

	protected final Sender sender;

}