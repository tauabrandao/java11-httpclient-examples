package br.com.study.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import br.com.study.models.Album;
import br.com.study.models.Comment;

public class Util {

	public static ArrayList<Album> montaListaDeAlbuns(JSONArray arrayDeAlbuns) {
		
		List<Album> listaDeAlbuns = new ArrayList<Album>();

		for (int i = 0; i < arrayDeAlbuns.length(); i++) {

			JSONObject jsonObj = arrayDeAlbuns.getJSONObject(i);
			Album alb = new Gson().fromJson(jsonObj.toString(), Album.class);
			listaDeAlbuns.add(alb);
		}

		return (ArrayList<Album>) listaDeAlbuns;

	}

	public static ArrayList<Comment> montaListaDeComents(JSONArray arrayDeComments) {
		List<Comment> listaDeComments = new ArrayList<Comment>();

		for (int i = 0; i < arrayDeComments.length(); i++) {

			JSONObject jsonObj = arrayDeComments.getJSONObject(i);
			Comment comm = new Gson().fromJson(jsonObj.toString(), Comment.class);
			listaDeComments.add(comm);
		}

		return (ArrayList<Comment>) listaDeComments;
	}
	
	public static void threadSleep(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
