package com.vladooha.data.dto;

import com.vladooha.data.entity.Profile;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfileDTO {
    public ProfileDTO() { }
    public ProfileDTO(Profile profile) {
        if (profile != null) {
            id = profile.getId();
            name = profile.getName();
            surname = profile.getSurname();
            home = new HomeDTO(profile.getHome());
        }
    }

    private long id;
    private String name;
    private String surname;
    private HomeDTO home;
}
