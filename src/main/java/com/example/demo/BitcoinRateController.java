package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinRateController {

    private final BitcoinRateService bitcoinRateService;

    @Autowired
    public BitcoinRateController(BitcoinRateService bitcoinRateService) {
        this.bitcoinRateService = bitcoinRateService;
    }

//    @GetMapping("/bitcoin-rate")
//    public String getBitcoinRate() {
@GetMapping("/bitcoin-rate")
public String getBitcoinRate() {
    String rateInWords = bitcoinRateService.getBitcoinRateInWords();
    return "Bitcoin Rate: " + rateInWords;
}
}

