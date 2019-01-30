package user.service;

import exception.BusinessException;
import exception.ExceptionCode;
import user.dao.UserPersistenceManager;
import user.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Stateless
public class EmployeeManagementService {

    @EJB
    UserPersistenceManager userPersistenceManager;

    /**
     * Adds a employee(given by Email) to the list of employees of  supervisor(given by Email), and sets the supervisor
     * Email of the given employee to be the given supervisorEmail
     * @param supervisorEmail
     * @param employeeEmail
     * @throws BusinessException
     */
    public void addEmployeeToSupervisor(String supervisorEmail, String employeeEmail) throws BusinessException{

        Optional<User> supervisorOptional = userPersistenceManager.getUserByEmail(supervisorEmail);
        Optional<User> employeeOptional = userPersistenceManager.getUserByEmail(employeeEmail);

        if(supervisorOptional.isPresent() && employeeOptional.isPresent() && !supervisorEmail.equals(employeeEmail)){
            User supervisor = supervisorOptional.get();
            User employee = employeeOptional.get();
            validateSupervisor(supervisor);
            if (employee.getSupervisorMail() != null) {
                removeEmployeeFromSupervisor(employee.getSupervisorMail(), employee.getEmail());
            }
            supervisor.getEmployees().add(employee);
            employee.setSupervisorMail(supervisorEmail);

        }else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }

    private void validateSupervisor(User supervisor) throws BusinessException{
        AtomicBoolean ok = new AtomicBoolean(false);
        supervisor.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> {
                if(permission.getType().equals("PROFILE_REVIEW") )
                    ok.set(true);
            });
        });
        if(!ok.get())
            throw new BusinessException(ExceptionCode.USER_PERMISSION_VALIDATION);
    }

    /**
     * Removes employee from the given supervisor.
     * @param supervisorEmail
     * @param employeeEmail
     * @throws BusinessException
     */
    public void removeEmployeeFromSupervisor(String supervisorEmail, String employeeEmail)throws BusinessException{
        Optional<User> supervisorOptional = userPersistenceManager.getUserByEmail(supervisorEmail);
        Optional<User> employeeOptional = userPersistenceManager.getUserByEmail(employeeEmail);
        if(supervisorOptional.isPresent() && employeeOptional.isPresent()){
            User supervisor = supervisorOptional.get();
            User employee = employeeOptional.get();
            validateSupervisor(supervisor);
            if(supervisor.getEmployees().contains(employee) && employee.getSupervisorMail().equals(supervisorEmail) ){
                supervisor.getEmployees().remove(employee);
                employee.setSupervisorMail(null);
            }else
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }

    /**
     * Gets the list of employees of the given supervisor
     * @param supervisorEmail
     * @return
     * @throws BusinessException
     */
    public List<User> getAllEmployeesFromSupervisor(String supervisorEmail)throws BusinessException{
        Optional<User> supervisorOptional = userPersistenceManager.getUserByEmail(supervisorEmail);
        if(supervisorOptional.isPresent()){
            User supervisor = supervisorOptional.get();
            return supervisor.getEmployees();
        }else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }

    /**
     * Gets all supervisors.
     * @return
     */
    public List<User> getAllSupervisors(){
        List<User> users = userPersistenceManager.getAllUsers();
        List<User> supervisors = new ArrayList<>();
        users.forEach(user -> {
            user.getRoles().forEach(role -> {
                if(role.getType().equals("SUPERVISOR"))
                    supervisors.add(user);
            });
        });
        return supervisors;
    }


}
