package dto.employee;

//DTO: Employee response
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
 private Long id;
 private String firstName;
 private String lastName;
 private String email;
 private String phone;
 private String position;
 private Long managerId;
}
