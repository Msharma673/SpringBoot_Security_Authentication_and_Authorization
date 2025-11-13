package dto.manager;

//DTO: Manager response
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
 private Long id;
 private String name;
 private String designation;
 private Integer experience;
 private String city;
}
