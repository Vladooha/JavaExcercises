package com.vladooha.service;

import com.vladooha.data.entity.Home;
import com.vladooha.dto.HomeDTO;
import com.vladooha.dto.HomeProfilesDTO;
import com.vladooha.dto.ProfileDTO;
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

//    public List<HomeProfilesDTO> findAllDTO() {
//        List<Home> homeList = homeService.findAllDTO();
//        List<HomeProfilesDTO> result = new ArrayList<>();
//        for (Home home : homeList) {
//            HomeProfilesDTO homeProfilesDTO = new HomeProfilesDTO(home);
//            result.add(homeProfilesDTO);
//        }
//
//        return result;
//    }

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
        System.err.println("SETTLE: profile - " + homeProfilesDTO.getProfileId() + ", home - " + homeProfilesDTO.getHomeId());
        Home home = homeService.findById(homeProfilesDTO.getHomeId());
        profileService.addHome(homeProfilesDTO.getProfileId(), home);
    }
}
