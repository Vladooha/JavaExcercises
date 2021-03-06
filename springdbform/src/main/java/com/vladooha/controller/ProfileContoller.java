package com.vladooha.controller;

import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.service.ProfileService;
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
public class ProfileContoller {
    public static final String NEW_PROFILE = "newProfile";
    public static final String INSERTED_PROFILE = "insertedProfile";

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public String getProfiles(Model model) {
        initTable(model);
        model.addAttribute(NEW_PROFILE, new ProfileDTO());

        return "profiles";
    }

    @PostMapping("/profile")
    public String addProfile(
            @Valid @ModelAttribute(NEW_PROFILE) ProfileDTO profileDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            initTable(model);
            model.addAttribute(NEW_PROFILE, profileDTO);
        } else {
            profileService.insertDTO(profileDTO);
            model.addAttribute(INSERTED_PROFILE, profileDTO);
            initTable(model);
        }

        return "profiles";
    }

    private void initTable(Model model) {
        List<ProfileDTO> profileDTOList = profileService.findAllDTO();
        model.addAttribute("profiles", profileDTOList);
    }
}
