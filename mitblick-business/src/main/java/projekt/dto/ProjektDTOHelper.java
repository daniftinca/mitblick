package projekt.dto;

import projekt.entities.Projekt;

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
