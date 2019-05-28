package com.vladooha.data.entity;

import com.vladooha.data.dto.HomeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(
        name = "home",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"adress"})})
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String adress;
    private int stageCount;
    @OneToMany(mappedBy = "home")
    @EqualsAndHashCode.Exclude
    private List<Profile> profiles;

    public boolean mergeDTOwithouthId(HomeDTO homeDTO) {
        if (homeDTO != null) {
            adress = homeDTO.getAdress();
            stageCount = homeDTO.getStageCount();

            return true;
        }

        return false;
    }

    public boolean mergeDTOwithId(HomeDTO homeDTO) {
        if (homeDTO != null) {
            id = homeDTO.getId();

            mergeDTOwithouthId(homeDTO);

            return true;
        }

        return false;
    }

    public boolean fullCopyDTO(HomeDTO homeDTO) {
        if (homeDTO != null) {
            profiles = null;

            mergeDTOwithId(homeDTO);

            return true;
        }

        return false;
    }
}
