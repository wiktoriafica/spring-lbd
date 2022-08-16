package pl.fis.springlbdday2.dto.currency;

import lombok.Data;

@Data
public class CurrencyDto {
    private String currency;
    private String code;
    private double mid;
}
