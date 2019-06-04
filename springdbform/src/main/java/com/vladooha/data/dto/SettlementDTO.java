package com.vladooha.data.dto;

import com.vladooha.data.validator.annotation.IsHome;
import com.vladooha.data.validator.annotation.IsProfile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class SettlementDTO {
    public SettlementDTO() { }
    public SettlementDTO(ProfileDTO profileDTO, HomeDTO homeDTO) {
        this.profileDTO = profileDTO;
        this.homeDTO = homeDTO;
        this.profileId = profileDTO.getId();
        this.homeId = homeDTO.getId();
    }

    @Min(value = 1L, message = "Неверный формта идентификатора")
    @IsProfile
    private long profileId;
    @Min(value = 1L, message = "Неверный формта идентификатора")
    @IsHome
    private long homeId;
    @NotNull
    private ProfileDTO profileDTO;
    @NotNull
    private HomeDTO homeDTO;
}
