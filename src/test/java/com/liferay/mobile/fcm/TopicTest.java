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
import com.liferay.mobile.fcm.exception.InvalidTopicNameException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Farache
 */
public class TopicTest {

	@Test
	public void testTwoTopicsWithAnd() throws InvalidTopicNameException {
		Topic topic = new Condition(
			new Topic("a"), Operator.AND, new Topic("b"));

		assertEquals(Operator.AND, Operator.valueOf("AND"));
		assertEquals("'a' in topics && 'b' in topics", topic.toString());
	}

	@Test
	public void testTwoTopicsWithOr() throws InvalidTopicNameException {
		Topic topic = new Condition(
			new Topic("a"), Operator.OR, new Topic("b"));

		assertEquals(Operator.OR, Operator.valueOf("OR"));
		assertEquals("'a' in topics || 'b' in topics", topic.toString());
	}

	@Test
	public void testThreeTopicsWithOr() throws InvalidTopicNameException {
		Topic left = new Condition(
			new Topic("a"), Operator.OR, new Topic("b"));

		Topic right = new Topic("c");
		Topic topic = new Condition(left, Operator.OR, right);

		assertEquals(
			"'a' in topics || 'b' in topics || 'c' in topics",
			topic.toString());
	}

	@Test
	public void testMessageWithCondition() throws InvalidTopicNameException {
		Message message = new Message.Builder()
			.to(new Condition(new Topic("a"), Operator.OR, new Topic("b")))
			.build();

		String json = Sender.toJson(message);
		String condition = "'a' in topics || 'b' in topics";
		assertEquals(condition, message.condition());
		assertEquals(json, "{\"condition\":\"" + condition + "\"}");
	}

	@Test
	public void testMessageWithTopic() throws InvalidTopicNameException {
		Message message = new Message.Builder()
			.to(new Topic("a"))
			.build();

		String json = Sender.toJson(message);
		assertEquals(json, "{\"to\":\"/topics/a\"}");
	}

	@Test(expected = InvalidTopicNameException.class)
	public void testInvalidTopicName() throws InvalidTopicNameException {
		new Topic("&");
	}

}