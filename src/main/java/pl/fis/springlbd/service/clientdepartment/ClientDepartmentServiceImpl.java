package pl.fis.springlbd.service.clientdepartment;

import org.springframework.stereotype.Service;

@Service
public class ClientDepartmentServiceImpl implements ClientDepartmentService {
    private final ClientDepartmentService clientDepartmentService;

    public ClientDepartmentServiceImpl(ClientDepartmentService clientDepartmentService) {
        this.clientDepartmentService = clientDepartmentService;
    }
}
