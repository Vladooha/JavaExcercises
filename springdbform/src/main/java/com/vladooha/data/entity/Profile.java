package com.vladooha.data.entity;

import com.vladooha.dto.HomeDTO;
import com.vladooha.dto.ProfileDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    @ManyToOne
    @JoinColumn
    private Home home;

    public boolean mergeDTOwithouthId(ProfileDTO profileDTO) {
        if (profileDTO != null) {
            name = profileDTO.getName();
            surname = profileDTO.getSurname();

            if (profileDTO.getHome() != null) {
                Home homeFromDTO = new Home();
                homeFromDTO.mergeDTOwithId(profileDTO.getHome());
                home = homeFromDTO;
            }

            return true;
        }

        return false;
    }

    public boolean mergeDTOwithId(ProfileDTO profileDTO) {
        if (profileDTO != null) {
            id = profileDTO.getId();

            mergeDTOwithouthId(profileDTO);

            return true;
        }

        return false;
    }

    public boolean fullCopyDTO(ProfileDTO profileDTO) {
        if (profileDTO != null) {
            mergeDTOwithId(profileDTO);

            return true;
        }

        return false;
    }
}
