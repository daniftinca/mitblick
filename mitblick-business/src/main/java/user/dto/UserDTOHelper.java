package user.dto;

import user.entities.User;
import utils.Encryptor;

public class UserDTOHelper {

    private UserDTOHelper() {
        //empty private constructor to hide the public implicit one
    }

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setActive(user.getActive());
        userDTO.setSupervisorEmail(user.getSupervisorMail());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setActive(userDTO.getActive());
        user.setSupervisorMail(userDTO.getSupervisorEmail());
        return user;

    }


    public static User updateEntityWithDTO(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        if (userDTO.getActive() != null) {
            user.setActive(userDTO.getActive());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(Encryptor.encrypt(userDTO.getPassword()));
        }
        if (userDTO.getSupervisorEmail() != null) {
            user.setSupervisorMail(userDTO.getSupervisorEmail());
        }
        return user;
    }
}


