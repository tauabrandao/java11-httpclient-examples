package br.com.study.requests;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import com.google.gson.Gson;

import br.com.study.models.Album;
import br.com.study.models.Comment;
import br.com.study.models.Post;
import br.com.study.util.Parser;
import br.com.study.util.Util;

//a partir do JDK 11

public class ContemporaryMethod {

	public static void getAlbunsAssyncronous() {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();

		System.out.println("sending assyncronous request to get albums");
		JSONArray responseArray = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body).thenApply(Parser::parseToArray).join();

		ArrayList<Album> listaDeAlbuns = Util.montaListaDeAlbuns(responseArray);

		System.out.println("printing albums response");
		System.out.println(responseArray);

	}

	public static void getAlbunsSyncronous() {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println("status code: " + response.statusCode());
			System.out.println("----------------------------------------------");
			System.out.println("response body: " + response.body());

			System.out.println("----------------------------------------------");
			HttpHeaders headers = response.headers();
			headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getCommentsAssyncronous() {

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("https://jsonplaceholder.typicode.com/comments")).build();
		System.out.println("sending assyncronous request to get comments");
		JSONArray responseArray = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body).thenApply(Parser::parseToArray).join();

		ArrayList<Comment> listaDeComments = Util.montaListaDeComents(responseArray);

		System.out.println("printing comments response");
		System.out.println(responseArray);

	}

	public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
		var builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
				builder.append("&");
			}
			builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
			builder.append("=");
			builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

	public static void sendPostFormParameters() {
		// form parameters
		Map<Object, Object> data = new HashMap<>();
		data.put("title", "my title");
		data.put("body", "my body");
		data.put("userId", 1);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().POST(ofFormData(data))
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
				.header("Content-Type", "application/x-www-form-urlencoded").build();

		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

	}

	public static void sendPostJSON() {

		HttpClient client = HttpClient.newHttpClient();

		Post post = new Post();
		post.setBody("This is my body");
		post.setTitle("This is the title of my post");
		post.setId(10L);
		post.setUserId(2L);

		// add json header
		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(post)))
				.uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Status code: " + response.statusCode());
		System.out.println("Response Body: " + response.body());

	}

}
