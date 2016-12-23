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
import com.liferay.mobile.fcm.exception.InvalidTopicName;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Bruno Farache
 */
public class TopicTest {

	@Test
	public void testConditionParentheses() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		Condition condition = new Condition(a, Operator.AND, b);
		condition.parentheses();

		String expected = "('a' in topics && 'b' in topics)";
		assertEquals(expected, condition.to());

		condition = new Condition(a).and(b).parentheses();
		assertEquals(expected, condition.to());

		condition = new Condition(condition).or(new Topic("c"));
		assertEquals(expected + " || 'c' in topics", condition.to());
	}

	@Test
	public void testTwoTopicsWithAnd() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To topic = new Condition(a, Operator.AND, b);
		assertEquals(Operator.AND, Operator.valueOf("AND"));

		String expected = "'a' in topics && 'b' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(a).and(b);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testTwoTopicsWithOr() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To topic = new Condition(a, Operator.OR, b);
		assertEquals(Operator.OR, Operator.valueOf("OR"));

		String expected = "'a' in topics || 'b' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(a).or(b);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testThreeTopicsWithAnd() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To left = new Condition(a, Operator.AND, b);

		Topic c = new Topic("c");
		To topic = new Condition(left, Operator.AND, c);

		String expected = "'a' in topics && 'b' in topics && 'c' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(left).and(c);
		assertEquals(expected, topic.to());

		topic = new Condition(a).and(b).and(c);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testThreeTopicsWithAndOr() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To left = new Condition(a, Operator.AND, b);

		Topic c = new Topic("c");
		To topic = new Condition(left, Operator.OR, c);

		String expected = "'a' in topics && 'b' in topics || 'c' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(left).or(c);
		assertEquals(expected, topic.to());

		topic = new Condition(a).and(b).or(c);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testThreeTopicsWithOr() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To left = new Condition(a, Operator.OR, b);

		Topic c = new Topic("c");
		To topic = new Condition(left, Operator.OR, c);

		String expected = "'a' in topics || 'b' in topics || 'c' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(left).or(c);
		assertEquals(expected, topic.to());

		topic = new Condition(a).or(b).or(c);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testThreeTopicsWithOrAnd() throws Exception {
		Topic a = new Topic("a");
		Topic b = new Topic("b");
		To left = new Condition(a, Operator.OR, b);

		Topic c = new Topic("c");
		To topic = new Condition(left, Operator.AND, c);

		String expected = "'a' in topics || 'b' in topics && 'c' in topics";
		assertEquals(expected, topic.to());

		topic = new Condition(left).and(c);
		assertEquals(expected, topic.to());

		topic = new Condition(a).or(b).and(c);
		assertEquals(expected, topic.to());
	}

	@Test
	public void testMessageWithCondition() throws Exception {
		Condition to = new Condition(
			new Topic("a"), Operator.OR, new Topic("b"));

		Message message = new Message.Builder()
			.to(to)
			.build();

		String json = Sender.toJson(message);
		String condition = "'a' in topics || 'b' in topics";
		assertEquals(condition, message.condition());
		assertEquals(json, "{\"condition\":\"" + condition + "\"}");
	}

	@Test
	public void testMessageWithTopic() throws Exception {
		String name = "a";
		Topic to = new Topic(name);
		assertEquals(name, to.name());

		Message message = new Message.Builder()
			.to(to)
			.build();

		String json = Sender.toJson(message);
		assertEquals(json, "{\"to\":\"/topics/" + name + "\"}");
	}

	@Test(expected = InvalidTopicName.class)
	public void testInvalidTopicName() throws InvalidTopicName {
		new Topic("&");
	}

}