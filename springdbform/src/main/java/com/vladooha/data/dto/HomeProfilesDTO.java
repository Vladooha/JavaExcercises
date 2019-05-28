package com.vladooha.data.dto;

import com.vladooha.data.validator.annotation.IsHome;
import com.vladooha.data.validator.annotation.IsProfile;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class HomeProfilesDTO {
    public HomeProfilesDTO() { }
    public HomeProfilesDTO(ProfileDTO profileDTO, HomeDTO homeDTO) {
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
    private ProfileDTO profileDTO;
    private HomeDTO homeDTO;
}
