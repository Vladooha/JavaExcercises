package com.vladooha.dto;

import com.vladooha.data.entity.Home;
import com.vladooha.data.entity.Profile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HomeProfilesDTO {
//    public HomeProfilesDTO(Home home) {
//        if (home != null) {
//            id = home.getId();
//            adress = home.getAdress();
//
//            if (home.getProfiles() != null) {
//                profiles = new ArrayList<>();
//                for (Profile profile : home.getProfiles()) {
//                    profiles.add(new ProfileDTO(profile));
//                }
//            }
//        }
//    }
//
//    private long id;
//    private String adress;
//    private List<ProfileDTO> profiles;
    public HomeProfilesDTO() { }
    public HomeProfilesDTO(ProfileDTO profileDTO, HomeDTO homeDTO) {
        this.profileDTO = profileDTO;
        this.homeDTO = homeDTO;
        this.profileId = profileDTO.getId();
        this.homeId = homeDTO.getId();
    }

    @Min(1L)
    private long profileId;
    @Min(1L)
    private long homeId;
    private ProfileDTO profileDTO;
    private HomeDTO homeDTO;
}
