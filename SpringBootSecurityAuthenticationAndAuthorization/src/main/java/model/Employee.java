package model;

//Employee entity with reference to Manager
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String firstName;
 private String lastName;

 @Column(nullable = false)
 private String email;

 private String phone;
 private String position;

 @ManyToOne
 @JoinColumn(name = "manager_id")
 private Manager manager;
}

