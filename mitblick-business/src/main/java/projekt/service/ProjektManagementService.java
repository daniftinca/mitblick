package projekt.service;

import exception.BusinessException;
import exception.ExceptionCode;
import profile.dao.ProfilePersistenceManager;
import profile.entities.Profile;
import projekt.dao.ProjektPersistenceManager;
import projekt.dto.ProjektDTO;
import projekt.dto.ProjektDTOHelper;
import projekt.entities.Projekt;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ProjektManagementService {

    @EJB
    private ProjektPersistenceManager projektPersistenceManager;

    @EJB
    private ProfilePersistenceManager profilePersistenceManager;

    /**
     * Create a Project entity using a ProjectDTO
     * @param projektDTO
     * @param email
     * @return
     * @throws BusinessException
     */
    public ProjektDTO create(ProjektDTO projektDTO) throws BusinessException {

        validateForCreation(projektDTO);
        Projekt projekt = ProjektDTOHelper.toEntity(projektDTO);
        projektPersistenceManager.create(projekt);

        return ProjektDTOHelper.fromEntity(projekt);

    }

    /**
     * Updates a project entity using a projectDTO.
     * @param projektDTO
     * @return
     * @throws BusinessException
     */
    public ProjektDTO update(ProjektDTO projektDTO) throws BusinessException {
        Optional<Projekt> projektBeforeOptional = projektPersistenceManager.getByName(projektDTO.getName());

        if (projektBeforeOptional.isPresent()) {
            Projekt projektBefore = projektBeforeOptional.get();
            validateForUpdate(projektDTO);
            Projekt projektAfter = ProjektDTOHelper.updateEntityWithDTO(projektBefore, projektDTO);

            projektPersistenceManager.update(projektAfter);

            return projektDTO;
        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }
    }

    /**
     * Deletes a project from a profile and then deletes the project entity.
     * @param projektDTO
     * @param email
     * @throws BusinessException
     */
    public void delete(ProjektDTO projektDTO, String email) throws BusinessException {
        Optional<Profile> profile = profilePersistenceManager.getByEmail(email);
        Optional<Projekt> projekt = projektPersistenceManager.getByName(projektDTO.getName());

        if (projekt.isPresent() && profile.isPresent()) {
            profile.get().getProjekts().remove(projekt.get());
            projektPersistenceManager.delete(projekt.get());

        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }

    }

    /**
     * Gets a list of all existing projects.
     * @return
     */
    public List<ProjektDTO> getAll() {
        return projektPersistenceManager.getAll()
                .stream()
                .map(ProjektDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Gets a project by Id.
     * @param id
     * @return
     * @throws BusinessException
     */
    public Projekt getById(Long id) throws BusinessException {
        Optional<Projekt> profileOptional = projektPersistenceManager.getById(id);
        if (profileOptional.isPresent()) {
            return profileOptional.get();
        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }
    }

    /**
     * Gets a project by name.
     * @param name
     * @return
     * @throws BusinessException
     */
    public Projekt getByName(String name) throws BusinessException {
        Optional<Projekt> profileOptional = projektPersistenceManager.getByName(name);
        if (profileOptional.isPresent()) {
            return profileOptional.get();
        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }
    }

    private void validateForCreation(ProjektDTO projektDTO) throws BusinessException {
        if (!isValidForCreation(projektDTO)) {
            throw new BusinessException(ExceptionCode.PROFILE_VALIDATION_EXCEPTION);
        }
    }

    private boolean isValidForCreation(ProjektDTO projektDTO) throws BusinessException {
        return validateFields(projektDTO);
    }

    private boolean validateFields(ProjektDTO projektDTO) {
        return
                projektDTO.getName() != null && projektDTO.getClient() != null && projektDTO.getBranch() != null &&
                        projektDTO.getStartDate() != null && projektDTO.getEndDate() != null;
    }

    private void validateForUpdate(ProjektDTO projektDTO) throws BusinessException {
        if (!validateFields(projektDTO)) {
            throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
        }
    }


}
