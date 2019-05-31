package com.vladooha.controller;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.HomeProfilesDTO;
import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.service.HomeService;
import com.vladooha.service.ProfileService;
import com.vladooha.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SettlementController {
    public static final String NEW_SETTLE = "newSettle";

    @Autowired
    private HomeService homeService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private SettlementService settlementService;

    @GetMapping("/settlement")
    public String getSettlement(Model model) {
        initTable(model);
        model.addAttribute(NEW_SETTLE, new HomeProfilesDTO());

        return "settle";
    }

    @PostMapping("/settlement")
    public String addSettlements(
            @Valid @ModelAttribute(NEW_SETTLE) HomeProfilesDTO homeProfilesDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            initTable(model);
            model.addAttribute(NEW_SETTLE, homeProfilesDTO);
        } else {
            settlementService.settle(homeProfilesDTO);
            initTable(model);
        }

        return "settle";
    }

    private void initTable(Model model) {
        List<HomeProfilesDTO> homeProfilesDTOList = settlementService.findAllDTO();
        List<ProfileDTO> profileDTOList = profileService.findAllDTO();
        List<HomeDTO> homeDTOList = homeService.findAllDTO();
        model.addAttribute("settlements", homeProfilesDTOList);
        model.addAttribute("profiles", profileDTOList);
        model.addAttribute("houses", homeDTOList);
    }
}
