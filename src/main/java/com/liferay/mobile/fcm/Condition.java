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

import com.liferay.mobile.fcm.exception.ExceededNumberOfOperators;

/**
 * @author Bruno Farache
 */
public class Condition implements To {

	public Condition(To left, Operator operator, To right)
		throws ExceededNumberOfOperators {

		this.left = left;
		this.operator = operator;
		this.right = right;
		countNumberOfTopics(this);
	}

	public Condition(To to) {
		this.left = to;
	}

	public Condition and(To to) throws ExceededNumberOfOperators {
		if (operator != null) {
			left = new Condition(left, operator, right);
		}

		right = to;
		operator = Operator.AND;
		countNumberOfTopics(this);
		return this;
	}

	public Condition or(To to) throws ExceededNumberOfOperators {
		if (operator != null) {
			left = new Condition(left, operator, right);
		}

		right = to;
		operator = Operator.OR;
		countNumberOfTopics(this);
		return this;
	}

	public Condition parentheses() {
		parentheses = true;
		return this;
	}

	@Override
	public String to() {
		StringBuilder sb = new StringBuilder();

		if (parentheses) {
			sb.append("(");
		}

		sb.append(left.to());
		sb.append(" ");
		sb.append(operator.value);
		sb.append(" ");
		sb.append(right.to());

		if (parentheses) {
			sb.append(")");
		}

		return sb.toString();
	}

	protected int countNumberOfTopics(To to)
		throws ExceededNumberOfOperators {

		if (to instanceof Topic) {
			return 1;
		}

		Condition condition = (Condition)to;

		int sum = countNumberOfTopics(condition.left) +
			countNumberOfTopics(condition.right);

		if (sum > 3) {
			throw new ExceededNumberOfOperators(this);
		}

		return sum;
	}

	protected To left;
	protected boolean parentheses;
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