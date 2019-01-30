package projekt.dto;

import projekt.entities.Projekt;

import java.util.ArrayList;
import java.util.List;

public class ProjektDTOHelper {

    private ProjektDTOHelper() {
        //empty private constructor to hide the public implicit one
    }

    public static ProjektDTO fromEntity(Projekt projekt) {

        ProjektDTO projektDTO = new ProjektDTO();

        projektDTO.setId(projekt.getId());
        projektDTO.setName(projekt.getName());
        projektDTO.setClient(projekt.getClient());
        projektDTO.setBranch(projekt.getBranch());
        projektDTO.setStartDate(projekt.getStartDate());
        projektDTO.setEndDate(projekt.getEndDate());
        projektDTO.setDescription(projekt.getDescription());

        return projektDTO;
    }

    public static Projekt toEntity(ProjektDTO projektDTO) {

        Projekt projekt = new Projekt();

        projekt.setId(projektDTO.getId());
        projekt.setName(projektDTO.getName());
        projekt.setClient(projektDTO.getClient());
        projekt.setBranch(projektDTO.getBranch());
        projekt.setStartDate(projektDTO.getStartDate());
        projekt.setEndDate(projektDTO.getEndDate());
        projekt.setDescription(projektDTO.getDescription());

        return projekt;
    }

    public static List<ProjektDTO> fromEntity(List<Projekt> projekts) {
        List<ProjektDTO> projektDTOs = new ArrayList<>();
        for (Projekt projekt : projekts) {
            projektDTOs.add(fromEntity(projekt));
        }
        return projektDTOs;
    }

    public static List<Projekt> toEntity(List<ProjektDTO> projektDTOs) {
        List<Projekt> projekts = new ArrayList<>();
        for (ProjektDTO projekt : projektDTOs) {
            projekts.add(toEntity(projekt));
        }
        return projekts;
    }

    public static Projekt updateEntityWithDTO(Projekt projekt, ProjektDTO projektDTO) {
        projekt.setId(projektDTO.getId());
        projekt.setName(projektDTO.getName());
        projekt.setClient(projektDTO.getName());
        projekt.setBranch(projektDTO.getBranch());
        projekt.setStartDate(projektDTO.getStartDate());
        projekt.setEndDate(projektDTO.getEndDate());

        if (projektDTO.getDescription() != null) {
            projekt.setDescription(projektDTO.getDescription());
        }

        return projekt;
    }


}
