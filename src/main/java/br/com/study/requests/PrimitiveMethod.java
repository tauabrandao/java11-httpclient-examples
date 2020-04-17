package br.com.study.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;

import br.com.study.util.Parser;

public class PrimitiveMethod {

	public static void getAlbuns() {

		HttpURLConnection connection = null;

		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();
		try {
			URL url = new URL("https://jsonplaceholder.typicode.com/albums");
			connection = (HttpURLConnection) url.openConnection();

			// request setup
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			reader = new BufferedReader(
					new InputStreamReader(status > 299 ? connection.getErrorStream() : connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				responseContent.append(line);
			}
			reader.close();

			JSONArray responseArray = Parser.parseToArray(responseContent.toString());
			System.out.println(responseArray);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}

	}

}
