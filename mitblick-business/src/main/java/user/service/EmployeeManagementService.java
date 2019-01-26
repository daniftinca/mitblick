package user.service;

import exception.BusinessException;
import exception.ExceptionCode;
import user.dao.UserPersistenceManager;
import user.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Stateless
public class EmployeeManagementService {

    @EJB
    UserPersistenceManager userPersistenceManager;

    public void addEmployeeToSupervisor(String supervisorEmail, String employeeEmail) throws BusinessException{

        Optional<User> supervisorOptional = userPersistenceManager.getUserByEmail(supervisorEmail);
        Optional<User> employeeOptional = userPersistenceManager.getUserByEmail(employeeEmail);

        if(supervisorOptional.isPresent() && employeeOptional.isPresent()){
            User supervisor = supervisorOptional.get();
            User employee = employeeOptional.get();
            validateSupervisor(supervisor);
            supervisor.getEmployees().add(employee);
            employee.setSupervisorMail(supervisorEmail);

        }else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }

    public void validateSupervisor(User supervisor) throws BusinessException{
        AtomicBoolean ok = new AtomicBoolean(false);
        supervisor.getRoles().forEach(role -> {
            role.getPermissions().forEach(permission -> {
                if(permission.getType() == "PROFILE_REVIEW")
                    ok.set(true);
            });
        });
        if(ok.get()==false)
            throw new BusinessException(ExceptionCode.USER_PERMISSION_VALIDATION);
    }

    public void removeEmployeeFromSupervisor(String supervisorEmail, String employeeEmail)throws BusinessException{
        Optional<User> supervisorOptional = userPersistenceManager.getUserByEmail(supervisorEmail);
        Optional<User> employeeOptional = userPersistenceManager.getUserByEmail(employeeEmail);
        if(supervisorOptional.isPresent() && employeeOptional.isPresent()){
            User supervisor = supervisorOptional.get();
            User employee = employeeOptional.get();
            validateSupervisor(supervisor);
            if(supervisor.getEmployees().contains(employee) && employee.getSupervisorMail() == supervisorEmail){
                supervisor.getEmployees().remove(employee);
                employee.setSupervisorMail(null);
            }else
                throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
        }else
            throw new BusinessException(ExceptionCode.USER_VALIDATION_EXCEPTION);
    }


}