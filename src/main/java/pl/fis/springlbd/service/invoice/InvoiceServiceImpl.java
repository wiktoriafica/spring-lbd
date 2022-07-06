package pl.fis.springlbd.service.invoice;

import org.springframework.stereotype.Service;
import pl.fis.springlbd.service.client.ClientService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final ClientService clientService;

    public InvoiceServiceImpl(ClientService clientService) {
        this.clientService = clientService;
    }
}
