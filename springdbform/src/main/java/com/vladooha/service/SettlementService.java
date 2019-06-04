package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.SettlementDTO;
import com.vladooha.data.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementService {
    @Autowired
    private HomeService homeService;
    @Autowired
    private ProfileService profileService;

    public List<SettlementDTO> findAllDTO() {
        List<ProfileDTO> settledProfiles = profileService.findAllSettledDTO();
        List<SettlementDTO> result = new ArrayList<>();
        for (ProfileDTO settledProfile : settledProfiles) {
            HomeDTO homeDTO = settledProfile.getHome();
            SettlementDTO settlementDTO = new SettlementDTO(settledProfile, homeDTO);
            result.add(settlementDTO);
        }

        return result;
    }

    public void settle(SettlementDTO settlementDTO) {
        Home home = homeService.findById(settlementDTO.getHomeId());
        profileService.addHome(settlementDTO.getProfileId(), home);
    }
}
