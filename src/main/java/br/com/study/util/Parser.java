package br.com.study.util;

import org.json.JSONArray;

public class Parser {

	public static JSONArray parseToArray(String responseBody) {
		JSONArray arr = new JSONArray(responseBody);
		return arr;
	}

}
