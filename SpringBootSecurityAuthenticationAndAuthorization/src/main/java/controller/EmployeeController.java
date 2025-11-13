package controller;

//Employee REST controller with mixed authorization rules
import dto.employee.*;
import model.Employee;
import model.Manager;
import repository.EmployeeRepository;
import repository.ManagerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
 private final EmployeeRepository employeeRepository;
 private final ManagerRepository managerRepository;

 @Autowired
 public EmployeeController(EmployeeRepository employeeRepository, ManagerRepository managerRepository) {
     this.employeeRepository = employeeRepository;
     this.managerRepository = managerRepository;
 }

 @PreAuthorize("hasAnyRole('USER','ADMIN')")
 @PostMapping
 public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeCreateRequest req) {
     Employee e = new Employee();
     e.setFirstName(req.getFirstName());
     e.setLastName(req.getLastName());
     e.setEmail(req.getEmail());
     e.setPhone(req.getPhone());
     e.setPosition(req.getPosition());
     if (req.getManagerId() != null) {
         Manager m = managerRepository.findById(req.getManagerId()).orElse(null);
         e.setManager(m);
     }
     Employee saved = employeeRepository.save(e);
     return ResponseEntity.ok(toDto(saved));
 }

 @PreAuthorize("hasAnyRole('ADMIN','USER')")
 @GetMapping("/{id}")
 public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
     Employee e = employeeRepository.findById(id).orElseThrow();
     return ResponseEntity.ok(toDto(e));
 }

 @PreAuthorize("hasAnyRole('ADMIN','USER')")
 @PutMapping("/{id}")
 public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @Valid @RequestBody EmployeeCreateRequest req) {
     Employee e = employeeRepository.findById(id).orElseThrow();
     e.setFirstName(req.getFirstName());
     e.setLastName(req.getLastName());
     e.setEmail(req.getEmail());
     e.setPhone(req.getPhone());
     e.setPosition(req.getPosition());
     if (req.getManagerId() != null) {
         Manager m = managerRepository.findById(req.getManagerId()).orElse(null);
         e.setManager(m);
     }
     Employee saved = employeeRepository.save(e);
     return ResponseEntity.ok(toDto(saved));
 }

 @PreAuthorize("hasRole('ADMIN')")
 @DeleteMapping("/{id}")
 public ResponseEntity<Void> delete(@PathVariable Long id) {
     employeeRepository.deleteById(id);
     return ResponseEntity.noContent().build();
 }

 @PreAuthorize("hasAnyRole('USER','ADMIN')")
 @GetMapping
 public ResponseEntity<List<EmployeeDto>> getAll() {
     List<EmployeeDto> list = employeeRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
     return ResponseEntity.ok(list);
 }

 private EmployeeDto toDto(Employee e) {
     Long mid = e.getManager() != null ? e.getManager().getId() : null;
     return new EmployeeDto(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(), e.getPhone(), e.getPosition(), mid);
 }
}
