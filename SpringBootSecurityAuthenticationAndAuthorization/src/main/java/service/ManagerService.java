package service;

//Manager service interface
import dto.manager.*;
import java.util.List;

public interface ManagerService {
 ManagerDto create(ManagerCreateRequest request);
 ManagerDto getById(Long id);
 List<ManagerDto> getAll();
 ManagerDto update(Long id, ManagerCreateRequest request);
 void delete(Long id);
}

