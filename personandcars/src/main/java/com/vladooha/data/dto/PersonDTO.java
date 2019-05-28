package com.vladooha.data.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vladooha.data.dto.serialize.DateDeserializer;
import com.vladooha.data.dto.serialize.DateSerializer;
import com.vladooha.data.validation.annotation.IsPast;
import com.vladooha.data.validation.annotation.PersonNotExists;
import com.vladooha.data.validation.grouping.FirstOrder;
import com.vladooha.data.validation.grouping.SecondOrder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
public class PersonDTO {
    @NotNull(groups = {FirstOrder.class}, message = "null_id")
    @Min(value = 0, message = "ID не может быть отрицательным", groups = {FirstOrder.class})
    @PersonNotExists(groups = {FirstOrder.class})
    private Long id;

    @NotNull(groups = {FirstOrder.class}, message = "null_name")
    private String name;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    @NotNull(groups = {FirstOrder.class}, message = "null_date")
    @IsPast(groups = {SecondOrder.class})
    private Date birthday;
}
