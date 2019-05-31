package com.vladooha.controller;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.service.HomeService;
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
public class HomeController {
    public static final String NEW_HOME = "newHome";
    public static final String INSERTED_HOME = "insertedHome";

    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public String getHouses(Model model) {
        initTable(model);
        model.addAttribute(NEW_HOME, new HomeDTO());

        return "houses";
    }

    @PostMapping("/home")
    public String addHome(
            @Valid @ModelAttribute(NEW_HOME) HomeDTO homeDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            initTable(model);
            model.addAttribute(NEW_HOME, homeDTO);
        } else {
            homeService.insertDTO(homeDTO);
            model.addAttribute(INSERTED_HOME, homeDTO);
            initTable(model);
        }

        return "houses";
    }

    private void initTable(Model model) {
        List<HomeDTO> homeDTOList = homeService.findAllDTO();
        model.addAttribute("houses", homeDTOList);
    }
}
