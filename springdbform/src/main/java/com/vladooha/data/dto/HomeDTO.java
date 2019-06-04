package com.vladooha.data.dto;

import com.vladooha.data.entity.Home;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class HomeDTO {
    public HomeDTO() { }
    public HomeDTO(Home home) {
        if (home != null) {
            id = home.getId();
            adress = home.getAdress();
            stageCount = home.getStageCount();
        }
    }

    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    private String adress;
    @NotNull
    @Min(value = 1, message = "В доме не может быть меньше одного этажа!")
    private Integer stageCount;
}
