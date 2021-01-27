package ru.vegd.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Employee implements Serializable {

    private static final long serialVersionUID = 1449233550861998884L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotEmpty
    @Column(name = "first_name", nullable = false, unique = true, length = 30)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false, unique = true, length = 30)
    private String lastName;

    @NotNull
    @Column(name = "department_id", nullable = false, unique = true)
    private Long departmentId;

    @NotEmpty
    @Column(name = "job_title", nullable = false, unique = true)
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, unique = true)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false, unique = true)
    private LocalDate dateOfBirth;
}
