package mapper;

import dto.manager.ManagerDto;
import model.Manager;

public class ManagerMapper {
 public static ManagerDto toDto(Manager m) {
     if (m==null) return null;
     return new ManagerDto(m.getId(), m.getName(), m.getDesignation(), m.getExperience(), m.getCity());
 }
}
