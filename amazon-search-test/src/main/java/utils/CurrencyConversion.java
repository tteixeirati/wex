package utils;

import static io.restassured.RestAssured.given;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

public class CurrencyConversion {

	public static String convertBrlUsd(String brlValueString) {

		Double usdValue;
		Float conversionRate;
		RestAssured.defaultParser = Parser.JSON;

		Double BrlValue=null;
			BrlValue = formatBrlToDouble(brlValueString);
		

		Response response = given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
				when().get("http://api.exchangeratesapi.io/v1/latest?access_key=09ee8497295e944995a3151f4a1e5e36&symbols=USD,BRL").
				then().contentType(ContentType.JSON).extract().response();

		Map<String,Float> jsonResponse = response.jsonPath().get("rates");

		conversionRate=jsonResponse.get("BRL")/jsonResponse.get("USD");

		usdValue = BrlValue/conversionRate;
		return String.format("%,.2f", usdValue);

	}

	public static Double formatBrlToDouble(String brlValue){

		Locale brazil = new Locale("pt", "BR");
		DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance(brazil);
		ParsePosition pos = new ParsePosition(0);
		Double result = format.parse("R$ ".concat(brlValue), pos).doubleValue();

		return result;
		
	}
	
}
