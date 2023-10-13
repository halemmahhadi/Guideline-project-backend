package hh.getData.guideline.Department;

import hh.getData.guideline.enumeration.Status;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RestController
@CrossOrigin
@RequestMapping("/departments/")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("getAll/")
    public ResponseEntity<List<DepartmentDto>> getAll() {
        try {
            List<Department> departments = departmentService.getAllDepartments();
            List<DepartmentDto> departmentDto = departments.stream().map(DepartmentDto::from).collect(Collectors.toList());
            return new ResponseEntity<>(departmentDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getById/id/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            if (department != null) {
                return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("create/")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        try {
            if (departmentDto.getStatus() == null) {
                departmentDto.setStatus(Status.ACTIVE);
            }
            Department department = departmentService.createDepartment(Department.from(departmentDto));
            return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(departmentService.deleteDepartment(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("update/id/{id}")
    public ResponseEntity<DepartmentDto> editDepartment(@PathVariable final Long id, @RequestBody final DepartmentDto departmentDto) {
        try {
            Department editedDepartment = departmentService.updateDepartment(id, Department.from(departmentDto));
            return new ResponseEntity<>(DepartmentDto.from(editedDepartment), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("soft-delete/id/{id}")
    public ResponseEntity<String> softDeleteById(@PathVariable Long id) {
        try {
            departmentService.softDeleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to soft delete record with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getByName/name/{name}")
    public ResponseEntity<DepartmentDto> getDepartmentByName(@PathVariable("name") String departmentName) {
        try {
            Department department = departmentService.getDepartmentByName(departmentName);
            if (department != null) {
                return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteAll/")
    public ResponseEntity<String> deleteAllDepartments() {
        try {
            departmentService.deleteAllDepartments();
            return new ResponseEntity<>("All departments have been deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete all departments.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "{departmentId}/licences/{licenceId}/add")
    public ResponseEntity<DepartmentDto> addLicenceToDepartment(@PathVariable final Long departmentId,
                                                                @PathVariable final Long licenceId) {
        try {
            Department department = departmentService.addLicenceToDepartment(departmentId, licenceId);
            return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "{departmentId}/licences/{licenceId}/remove")
    public ResponseEntity<DepartmentDto> removeLicence(@PathVariable final Long departmentId,
                                                                @PathVariable final Long licenceId) {
        try {
            Department department = departmentService.removeLicence(departmentId, licenceId);
            return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
