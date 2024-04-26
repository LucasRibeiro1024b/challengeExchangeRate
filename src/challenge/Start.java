package challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Start {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scan = new Scanner(System.in);
		String apiKey = null;
		
		/*
		System.out.print("Insert here your api key:");
		apiKey = scan.nextLine();
		System.out.println(apiKey);
		*/

		System.out.println("Insert here the base currency:");
		String base_code = scan.nextLine();
		
		try {
			File myObj = new File("src/challenge/apikey.txt");
			Scanner myReader = new Scanner(myObj);
			if (myReader.hasNextLine()) {
				apiKey = myReader.nextLine();
		    	System.out.println(apiKey);
			}
		    myReader.close();
	    } catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
	    }
		
		String url = "https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/"+base_code;
		
		var client = HttpClient.newHttpClient();
		
		var request = HttpRequest.newBuilder(
		       URI.create(url))
		   .header("accept", "application/json")
		   .build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		System.out.println(response.statusCode());
		//System.out.println(response.body());
		
		String json = response.body();
		//JsonObject object = new JsonObject(json);
		JsonElement parsedJson = JsonParser.parseString(json);
		JsonObject jsonobj = parsedJson.getAsJsonObject();
		System.out.println(jsonobj.get("result").getAsString());
		
		JsonObject currencies = jsonobj.getAsJsonObject("conversion_rates");
		System.out.println(currencies.get("USD").getAsString());
		
		
		scan.close();
	}
}
