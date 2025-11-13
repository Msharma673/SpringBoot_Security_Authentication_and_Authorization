package dto.manager;

//DTO: Manager create/update request

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ManagerCreateRequest {
 @NotBlank
 private String name;
 private String designation;
 private Integer experience;
 private String city;
}


