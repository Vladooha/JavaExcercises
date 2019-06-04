package com.vladooha.data.dto;

import com.vladooha.data.entity.Profile;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@EqualsAndHashCode
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

    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @EqualsAndHashCode.Exclude
    private HomeDTO home;
}
