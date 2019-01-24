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

    /*
    email -> profile
    profile.getProjekts.add()
     */
    public ProjektDTO create(ProjektDTO projektDTO, String email) throws BusinessException {

        validateForCreation(projektDTO);
        Projekt projekt = ProjektDTOHelper.toEntity(projektDTO);

        Optional<Profile> profile = profilePersistenceManager.getByEmail(email);

        if (profile.isPresent()) {
            projektPersistenceManager.create(projekt);

            profile.get().getProjekts().add(projekt);
        }

        return ProjektDTOHelper.fromEntity(projekt);

    }

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

    public void delete(ProjektDTO projektDTO) throws BusinessException {

        Optional<Projekt> projekt = projektPersistenceManager.getByName(projektDTO.getName());

        if (projekt.isPresent()) {

            projektPersistenceManager.delete(projekt.get());

        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }

    }

    public List<ProjektDTO> getAll() {
        return projektPersistenceManager.getAll()
                .stream()
                .map(ProjektDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    public Projekt getById(Long id) throws BusinessException {
        Optional<Projekt> profileOptional = projektPersistenceManager.getById(id);
        if (profileOptional.isPresent()) {
            return profileOptional.get();
        } else {
            throw new BusinessException(ExceptionCode.NAME_NOT_FOUND);
        }
    }

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
        /*if (projektPersistenceManager.getByName(projektDTO.getName()).isPresent()) {
            throw new BusinessException(ExceptionCode.NAME_EXISTS_ALREADY);
        }*/
        // putem avea proiecte cu acelasi nume
        return validateFields(projektDTO);
    }

    private boolean validateFields(ProjektDTO projektDTO) {
        return
                projektDTO.getName() != null && projektDTO.getClient() != null && projektDTO.getBranch() != null &&
                        projektDTO.getDate() != null;
    }

    private void validateForUpdate(ProjektDTO projektDTO) throws BusinessException {
        if (!validateFields(projektDTO)) {
            throw new BusinessException(ExceptionCode.PROJEKT_VALIDATION_EXCEPTION);
        }
    }


}
