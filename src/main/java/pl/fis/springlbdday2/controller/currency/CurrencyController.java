package pl.fis.springlbdday2.controller.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.fis.springlbdday2.dto.currency.RatesDto;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/rates")
public class CurrencyController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final static String baseUrl = "http://api.nbp.pl/api/exchangerates/tables/";

    @GetMapping
    public ResponseEntity<RatesDto[]> getCurrencyRates() {
        return ResponseEntity.ok().body(restTemplate
                .getForEntity(String.format("%sA/%s?format=JSON",
                baseUrl, LocalDate.now().minusDays(1)), RatesDto[].class)
                .getBody());
    }

    @GetMapping(path = "last10")
    public ResponseEntity<RatesDto[]> getLastTenCurrencyRates() {
        return ResponseEntity.ok().body(restTemplate
                .getForEntity(String.format("%sA/%s/%s?format=JSON", baseUrl,
                LocalDate.now().minusDays(10), LocalDate.now()), RatesDto[].class)
                .getBody());
    }
}
