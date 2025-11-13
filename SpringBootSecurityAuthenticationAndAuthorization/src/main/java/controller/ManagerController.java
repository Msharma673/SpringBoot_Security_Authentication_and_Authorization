package controller;

//Manager REST controller with role-based access using @PreAuthorize
import dto.manager.*;
import service.ManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {
 private final ManagerService managerService;
 
 @Autowired
 public ManagerController(ManagerService managerService) { this.managerService = managerService; }

 @PreAuthorize("hasRole('ADMIN')")
 @PostMapping
 public ResponseEntity<ManagerDto> create(@Valid @RequestBody ManagerCreateRequest request) {
     return ResponseEntity.ok(managerService.create(request));
 }

 @PreAuthorize("hasAnyRole('ADMIN','USER')")
 @GetMapping("/{id}")
 public ResponseEntity<ManagerDto> getById(@PathVariable Long id) {
     return ResponseEntity.ok(managerService.getById(id));
 }

 @PreAuthorize("hasRole('ADMIN')")
 @GetMapping
 public ResponseEntity<List<ManagerDto>> getAll() {
     return ResponseEntity.ok(managerService.getAll());
 }

 @PreAuthorize("hasRole('ADMIN')")
 @PutMapping("/{id}")
 public ResponseEntity<ManagerDto> update(@PathVariable Long id, @Valid @RequestBody ManagerCreateRequest request) {
     return ResponseEntity.ok(managerService.update(id, request));
 }

 @PreAuthorize("hasRole('ADMIN')")
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> delete(@PathVariable Long id) {
     managerService.delete(id);
     return ResponseEntity.noContent().build();
 }
}

