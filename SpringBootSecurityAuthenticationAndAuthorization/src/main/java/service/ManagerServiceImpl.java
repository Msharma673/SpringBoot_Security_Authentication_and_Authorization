package service;

//Manager service implementation


import com.example.corporate.dto.manager.*;
import com.example.corporate.mapper.ManagerMapper;
import com.example.corporate.model.Manager;
import com.example.corporate.repository.ManagerRepository;
import com.example.corporate.exception.ResourceNotFoundException;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
 private final ManagerRepository managerRepository;
 private final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

 public ManagerServiceImpl(ManagerRepository managerRepository) {
     this.managerRepository = managerRepository;
 }

 @Override
 public ManagerDto create(ManagerCreateRequest request) {
     Manager m = new Manager();
     m.setName(request.getName());
     m.setDesignation(request.getDesignation());
     m.setExperience(request.getExperience());
     m.setCity(request.getCity());
     Manager saved = managerRepository.save(m);
     logger.info("Created manager id={}", saved.getId());
     return ManagerMapper.toDto(saved);
 }

 @Override
 public ManagerDto getById(Long id) {
     Manager m = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
     return ManagerMapper.toDto(m);
 }

 @Override
 public List<ManagerDto> getAll() {
     return managerRepository.findAll().stream().map(ManagerMapper::toDto).collect(Collectors.toList());
 }

 @Override
 public ManagerDto update(Long id, ManagerCreateRequest request) {
     Manager m = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
     m.setName(request.getName());
     m.setDesignation(request.getDesignation());
     m.setExperience(request.getExperience());
     m.setCity(request.getCity());
     Manager saved = managerRepository.save(m);
     logger.info("Updated manager id={}", saved.getId());
     return ManagerMapper.toDto(saved);
 }

 @Override
 public void delete(Long id) {
     Manager m = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
     managerRepository.delete(m);
     logger.info("Deleted manager id={}", id);
 }
}

