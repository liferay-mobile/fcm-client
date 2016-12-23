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
public class Condition implements To {

	public Condition(To left, Operator operator, To right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	public Condition(To to) {
		this.left = to;
	}

	public Condition and(To to) {
		if (operator != null) {
			left = new Condition(left, operator, right);
		}

		right = to;
		operator = Operator.AND;
		return this;
	}

	public Condition or(To to) {
		if (operator != null) {
			left = new Condition(left, operator, right);
		}

		right = to;
		operator = Operator.OR;
		return this;
	}

	@Override
	public String to() {
		return left.to() + " " + operator.value + " " + right.to();
	}

	protected To left;
	protected Operator operator;
	protected To right;


	public enum Operator {
		AND("&&"), OR("||");

		Operator(String value) {
			this.value = value;
		}

		final String value;

	}

}