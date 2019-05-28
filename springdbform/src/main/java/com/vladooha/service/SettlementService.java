package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.HomeProfilesDTO;
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

    public List<HomeProfilesDTO> findAllDTO() {
        List<ProfileDTO> settledProfiles = profileService.findAllSettledDTO();
        List<HomeProfilesDTO> result = new ArrayList<>();
        for (ProfileDTO settledProfile : settledProfiles) {
            HomeDTO homeDTO = settledProfile.getHome();
            HomeProfilesDTO homeProfilesDTO = new HomeProfilesDTO(settledProfile, homeDTO);
            result.add(homeProfilesDTO);
        }

        return result;
    }

    public void settle(HomeProfilesDTO homeProfilesDTO) {
        Home home = homeService.findById(homeProfilesDTO.getHomeId());
        profileService.addHome(homeProfilesDTO.getProfileId(), home);
    }
}
