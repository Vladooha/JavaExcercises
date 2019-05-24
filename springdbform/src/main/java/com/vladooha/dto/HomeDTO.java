package com.vladooha.dto;

import com.vladooha.data.entity.Home;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HomeDTO {
    public HomeDTO() { }
    public HomeDTO(Home home) {
        if (home != null) {
            id = home.getId();
            adress = home.getAdress();
            stageCount = home.getStageCount();
        }
    }

    private long id;
    private String adress;
    @Min(1)
    private Integer stageCount;
}
