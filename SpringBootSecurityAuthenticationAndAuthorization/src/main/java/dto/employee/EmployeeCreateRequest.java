package dto.employee;

//DTO: Employee create/update request

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeCreateRequest {
 @NotBlank private String firstName;
 private String lastName;
 @NotBlank @Email private String email;
 private String phone;
 private String position;
 private Long managerId;
}

