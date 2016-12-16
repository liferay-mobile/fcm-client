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

/**
 * @author Bruno Farache
 */
public class Condition extends Topic {

	public Condition(Topic left, Operator operator, Topic right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public String toString() {
		return left.toString() + operator.value + right.toString();
	}

	protected Topic left;
	protected Operator operator;
	protected Topic right;


	protected enum Operator {
		AND(" && "), OR(" || ");

		Operator(String value) {
			this.value = value;
		}

		final String value;

	}

}