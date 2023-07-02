package com.example.demo;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BitcoinRateService {

    public String getBitcoinRateInWords() {
        String apiUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

        // Make an HTTP GET request to the API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        String jsonResponse = responseEntity.getBody();

        // Parse the JSON response
        JSONObject json = new JSONObject(jsonResponse);
        JSONObject bpi = json.getJSONObject("bpi");
        JSONObject usd = bpi.getJSONObject("USD");
        String rateString = usd.getString("rate");

        // Remove commas and decimal part from the rate string
        String rateWithoutDecimal = rateString.replace(",", "").split("\\.")[0];

        // Convert the rate to words
        String rateInWords = convertToWords(rateWithoutDecimal);

        return rateInWords;
    }

    private String convertToWords(String rateString) {
        String[] units = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
                "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
        String[] tens = { "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

        int rate = Integer.parseInt(rateString);

        if (rate == 0) {
            return "Zero";
        }

        StringBuilder words = new StringBuilder();

        if (rate >= 1000) {
            words.append(convertToWords(String.valueOf(rate/1000))).append(" Thousand ");
            rate %= 1000;
        }

        if (rate >= 100) {
            words.append(units[rate / 100]).append(" Hundred ");
            rate %= 100;
        }

        if (rate >= 20) {
            words.append(tens[rate / 10]).append(" ");
            rate %= 10;
        }

        if (rate > 0) {
            words.append(units[rate]);
        }

        return words.toString().trim();
    }
}

