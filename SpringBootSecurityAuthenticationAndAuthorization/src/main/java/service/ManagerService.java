package service;

//Manager service interface
package com.example.corporate.service;

import com.example.corporate.dto.manager.*;
import java.util.List;

public interface ManagerService {
 ManagerDto create(ManagerCreateRequest request);
 ManagerDto getById(Long id);
 List<ManagerDto> getAll();
 ManagerDto update(Long id, ManagerCreateRequest request);
 void delete(Long id);
}

