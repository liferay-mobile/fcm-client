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

import com.liferay.mobile.fcm.Condition.Operator;
import com.liferay.mobile.fcm.exception.ExceededNumberOfOperators;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Bruno Farache
 */
public class ExceededNumberOfOperatorsTest {

	@Test
	public void testExceededNumberOfOperators1() throws Exception {
		Condition condition1 = new Condition(
			new Topic("a"), Operator.AND, new Topic("b"));

		Condition condition2 = new Condition(
			new Topic("c"), Operator.AND, new Topic("d"));

		ExceededNumberOfOperators expected = null;

		try {
			new Condition(condition1, Operator.AND, condition2);
		}
		catch (ExceededNumberOfOperators e) {
			expected = e;
		}

		assertNotNull(expected);
	}

	@Test
	public void testExceededNumberOfOperators2() throws Exception {
		Condition condition1 = new Condition(
			new Topic("a"), Operator.AND, new Topic("b"));

		Condition condition2 = new Condition(
			new Topic("c"), Operator.AND, new Topic("d"));

		ExceededNumberOfOperators expected = null;

		try {
			new Condition(condition1)
				.and(condition2);
		}
		catch (ExceededNumberOfOperators e) {
			expected = e;
		}

		assertNotNull(expected);
	}

	@Test
	public void testExceededNumberOfOperators3() throws Exception {
		Condition condition1 = new Condition(
			new Topic("a"), Operator.AND, new Topic("b"));

		Condition condition2 = new Condition(
			new Topic("c"), Operator.AND, new Topic("d"));

		ExceededNumberOfOperators expected = null;

		try {
			new Condition(condition1)
				.or(condition2);
		}
		catch (ExceededNumberOfOperators e) {
			expected = e;
		}

		assertNotNull(expected);
	}

	@Test
	public void testExceededNumberOfOperators4() throws Exception {
		ExceededNumberOfOperators expected = null;

		try {
			new Condition(new Topic("a"))
				.or(new Topic("b"))
				.or(new Topic("c"))
				.or(new Topic("d"));
		}
		catch (ExceededNumberOfOperators e) {
			expected = e;
		}

		assertNotNull(expected);
	}

	@Test
	public void testExceededNumberOfOperators5() throws Exception {
		ExceededNumberOfOperators expected = null;

		try {
			Condition condition = new Condition(
				new Topic("c"), Operator.AND, new Topic("d"));

			new Condition(new Topic("a"))
				.or(new Topic("b"))
				.or(condition);
		}
		catch (ExceededNumberOfOperators e) {
			expected = e;
		}

		assertNotNull(expected);
	}

}