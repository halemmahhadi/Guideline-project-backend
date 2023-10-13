package hh.getData.guideline.Department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq", initialValue = 1)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "departmentName", nullable = false)
    private String departmentName;

    @Column(name = "description", nullable = false)
    private String Description;


    @Column(name = "status", nullable = false, columnDefinition = "int default 1")
    @Enumerated(EnumType.ORDINAL)
    private Status status  ;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Licence> licences = new ArrayList<>();


    public static Department from(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDescription(departmentDto.getDescription());
        department.setStatus(departmentDto.getStatus());
        return department;
    }

    public void addLicence(Licence licence) {
        this.licences.add(licence);
        licence.getDepartments().add(this);
    }

    public void removeLicence(Licence licence) {
        this.licences.remove(licence);
        licence.getDepartments().remove(this);
    }
}
