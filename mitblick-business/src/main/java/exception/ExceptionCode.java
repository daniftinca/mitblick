package exception;

/**
 * Provides exception codes and description.
 */
public enum ExceptionCode {
    UNKNOWN_EXCEPTION(1000, "Unknown Exception"),
    USER_VALIDATION_EXCEPTION(1001, "Validation Exception"),
    EMAIL_EXISTS_ALREADY(1001, "Email already exists Exception"),
    EMAIL_NOT_FOUND(1001, "User is not registered with this email, or is not registered at all."),
    PASSWORD_NOT_VALID(1002, "Password not valid."),
    USER_DEACTIVATED(1004, "User deactivated"),
    TOKEN_EXPIRED(1005, "Token expired"),
    USER_PERMISSION_VALIDATION(2001, "User does not have this permission"),
    ROLE_DOESNT_EXIST(2002, "User does not have this permission"),
    PROFILE_VALIDATION_EXCEPTION(1001, "Validation Exception"),
    NAME_EXISTS_ALREADY(1001, "Name already exists Exception"),
    NAME_NOT_FOUND(1001, "A projekt eith this name does not exist."),
    SKILL_VALIDATION_EXCEPTION(3000, "Skill Validation Exception"),
    SKILL_NOT_FOUND(3001, "Skill does not exist"),
    SKILLAREA_VALIDATION_EXCEPTION(4000, "Skill area is not valid"),
    SKILL_SKILLAREA_VALIDATION_EXCEPTION(40001, "Skill area is not defined and can't be used for this skill"),
    SKILLAREA_OR_SKILL_VALIDATION_EXCEPTION(4002, "Skill or Skillarea does not exist."),
    SKILL_IN_SKILLAREA_VALIDATION_EXCEPTION(4003, "Skill already exists in Skillarea."),
    SKILL_NOT_IN_SKILLAREA_VALIDATION_EXCEPTION(4004, "Skill does not exist in Skillarea, can't be removed."),
    PROJEKT_VALIDATION_EXCEPTION(123, "Projekt validation exception");
    int id;
    String message;

    ExceptionCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }


    public String getMessage() {
        return message;
    }


}