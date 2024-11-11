package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*


@Controller
class EmployeesResolver(private val employeeRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeeRepository.findAll()

    @QueryMapping
    fun employeeById(@Argument id: String): Employee? = employeeRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        var employee = Employee(
            name = createEmployeeInput.name,
            dateOfBirth = createEmployeeInput.dateOfBirth,
            city = createEmployeeInput.city,
            salary = createEmployeeInput.salary,
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        return employeeRepository.save(employee)
    }

    @MutationMapping
    fun updateEmployee(
        @Argument employeeId: String,
        @Argument createEmployeeInput: CreateEmployeeInput
    ): Employee? {
        val existingEmployee = employeeRepository.findById(employeeId).orElse(null)

        if (existingEmployee != null) {
            existingEmployee.name = createEmployeeInput.name
            existingEmployee.dateOfBirth = createEmployeeInput.dateOfBirth
            existingEmployee.city = createEmployeeInput.city
            existingEmployee.salary = createEmployeeInput.salary
            existingEmployee.gender = createEmployeeInput.gender
            existingEmployee.email = createEmployeeInput.email

            return employeeRepository.save(existingEmployee)
        }
        return null
    }

    @MutationMapping
    fun deleteEmployee(@Argument employeeId: String): Boolean {
        return if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId)
            true
        } else {
            false
        }
    }
}

