package com.br.pi.FishAndChips.Desk;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeskDto {

    private long id;
    private Integer occupantNumber;

    private DeskState deskState;

    public DeskDto fromEntity(Desk desk){

        DeskDto dto = new DeskDto();
        dto.setId(desk.getId());
        dto.setOccupantNumber(desk.getOccupantNumber());
        dto.setDeskState(desk.getTableState());

        return dto;

    }

}
