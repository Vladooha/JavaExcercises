package com.vladooha.data.dto;

import com.vladooha.data.entity.Car;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class PersonStatisticDTO extends PersonDTO {
    private Set<CarDTO> cars;
}
