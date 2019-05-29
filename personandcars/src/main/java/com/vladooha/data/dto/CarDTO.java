package com.vladooha.data.dto;

import com.vladooha.data.validation.annotation.AdultPerson;
import com.vladooha.data.validation.annotation.CarNotExists;
import com.vladooha.data.validation.annotation.IsModel;
import com.vladooha.data.validation.annotation.PersonExists;
import com.vladooha.data.validation.grouping.FirstOrder;
import com.vladooha.data.validation.grouping.SecondOrder;
import com.vladooha.data.validation.grouping.ThirdOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CarDTO {
    @NotNull(groups = FirstOrder.class)
    @Min(value = 0, message = "ID не может быть отрицательным", groups = SecondOrder.class)
    @CarNotExists(groups = SecondOrder.class)
    private Long id;

    @NotNull(groups = FirstOrder.class)
    @IsModel(groups = SecondOrder.class)
    private String model;

    @NotNull(groups = FirstOrder.class)
    @Min(value = 1, message = "Мощность должна быть больше нуля", groups = SecondOrder.class)
    private Integer horsepower;

    @NotNull(groups = FirstOrder.class)
    @PersonExists(groups = SecondOrder.class)
    @AdultPerson(groups = ThirdOrder.class)
    private Long ownerId;
}
