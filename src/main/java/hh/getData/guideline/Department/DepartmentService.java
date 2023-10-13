package hh.getData.guideline.Department;

import hh.getData.guideline.Exception.EntityNotFoundException;
import hh.getData.guideline.Exception.StatusAlreadyInactiveException;
import hh.getData.guideline.Licence.Licence;
import hh.getData.guideline.Licence.LicenceIsAlreadyAssignmentException;
import hh.getData.guideline.Licence.LicenceRepository;
import hh.getData.guideline.Licence.LicenceService;
import hh.getData.guideline.enumeration.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final LicenceService licenceService;

    public Department createDepartment(Department newDepartment) {
        log.info("Saving new Department information: {}", newDepartment.getDepartmentName());
        // You can set default values or perform any other logic here before saving
        return departmentRepository.save(newDepartment);
    }

    public List<Department> getAllDepartments() {
        log.info("Fetching all Departments");
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        log.info("Fetching Department by ID: {}", id);
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElseThrow();
    }

    public Department updateDepartment(Long id, Department departmentUpdate) {
        log.info("Updating Department information with ID {}: {}", id, departmentUpdate.getDepartmentName());

        Optional<Department> existingDepartmentOptional = departmentRepository.findById(id);

        if (existingDepartmentOptional.isPresent()) {
            Department existingDepartment = existingDepartmentOptional.get();
            // Update attributes with new values
            existingDepartment.setDepartmentName(departmentUpdate.getDepartmentName());
            existingDepartment.setDescription(departmentUpdate.getDescription());
            // Save the updated entity
            return departmentRepository.save(existingDepartment);
        } else {
            // Handle the case where the entity with the given ID does not exist
            throw new EntityNotFoundException("Department with ID " + id + " not found");
        }
    }

    public boolean deleteDepartment(Long id) {
        log.info("Deleting Department by ID: {}", id);
        departmentRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void softDeleteById(Long id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Department with ID " + id + " not found"));

            if (department.getStatus() != Status.INACTIVE) {
                department.setStatus(Status.INACTIVE);
                departmentRepository.save(department);
            } else {
                throw new StatusAlreadyInactiveException("Department with ID " + id + " is already INACTIVE");
            }
        } catch (Exception e) {
            log.error("An error occurred during soft deletion of Department with ID " + id, e);
        }
    }

    public Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName);
    }

    public void deleteAllDepartments() {
        departmentRepository.deleteAll();
    }

    @Transactional
    public Department addLicenceToDepartment(Long departmentId, Long licenceId){
        Department department = getDepartmentById(departmentId);
        Licence licence = licenceService.getLicenceById(licenceId);
        department.addLicence(licence);
        departmentRepository.save(department);
        return  department;
    }

    @Transactional
    public Department removeLicence(Long departmentId, Long licenceId){
        Department department = getDepartmentById(departmentId);
        Licence licence = licenceService.getLicenceById(licenceId);
        department.removeLicence(licence);
        return department;
    }
}
