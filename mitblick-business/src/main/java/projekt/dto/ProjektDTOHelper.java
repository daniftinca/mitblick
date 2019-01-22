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

        projektDTO.setName(projekt.getName());
        projektDTO.setClient(projekt.getClient());
        projektDTO.setBranch(projekt.getBranch());
        projektDTO.setDate(projekt.getDate().toString());
        projektDTO.setDescription(projekt.getDescription());

        return projektDTO;
    }

    public static Projekt toEntity(ProjektDTO projektDTO) {

        Projekt projekt = new Projekt();

        projekt.setName(projektDTO.getName());
        projekt.setClient(projektDTO.getName());
        projekt.setBranch(projektDTO.getBranch());
        projekt.setDate(projektDTO.getDate());
        projekt.setDescription(projektDTO.getDescription());

        return projekt;
    }

    public static List<ProjektDTO> fromEntity(List<Projekt> projekts) {
        List<ProjektDTO> projektDTOs = new ArrayList<ProjektDTO>();
        for (Projekt projekt : projekts) {
            projektDTOs.add(fromEntity(projekt));
        }
        return projektDTOs;
    }

    public static List<Projekt> toEntity(List<ProjektDTO> projektDTOs) {
        List<Projekt> projekts = new ArrayList<Projekt>();
        for (ProjektDTO projekt : projektDTOs) {
            projekts.add(toEntity(projekt));
        }
        return projekts;
    }

    public static Projekt updateEntityWithDTO(Projekt projekt, ProjektDTO projektDTO) {
        projekt.setName(projektDTO.getName());
        projekt.setClient(projektDTO.getName());
        projekt.setBranch(projektDTO.getBranch());
        projekt.setDate(projektDTO.getDate());

        if (projektDTO.getDescription() != null) {
            projekt.setDescription(projektDTO.getDescription());
        }

        return projekt;
    }


}
