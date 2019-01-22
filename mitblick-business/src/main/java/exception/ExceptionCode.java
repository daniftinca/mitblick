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
    SKILL_VALIDATION_EXCEPTION(3000, "Skill Validation Exception"),
    SKILL_NOT_FOUND(3001, "Skill does not exist"),
    SKILLAREA_VALIDATION_EXCEPTION(4000, "Skill area is not valid"),
    SKILL_SKILLAREA_VALIDATION_EXCEPTION(40001, "Skill area is not defined and can't be used for this skill");
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