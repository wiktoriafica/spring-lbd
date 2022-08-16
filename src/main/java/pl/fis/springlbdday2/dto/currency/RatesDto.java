package pl.fis.springlbdday2.dto.currency;

import lombok.Data;

@Data
public class RatesDto {
    private String table;
    private String no;
    private String effectiveDate;
    private CurrencyDto[] rates;
}
