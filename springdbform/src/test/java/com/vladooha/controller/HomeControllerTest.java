package com.vladooha.controller;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.service.HomeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService homeService;

    @Test
    public void getHouses_2HomesInDB_2HomesData() throws Exception {
        // Arrange
        HomeDTO fakeHome1 = new HomeDTO();
        fakeHome1.setId(1);
        fakeHome1.setAdress("Adress1");
        fakeHome1.setStageCount(1);

        HomeDTO fakeHome2 = new HomeDTO();
        fakeHome2.setId(2);
        fakeHome2.setAdress("Adress2");
        fakeHome2.setStageCount(2);

        List<HomeDTO> fakeHouses = new ArrayList<>();
        fakeHouses.add(fakeHome1);
        fakeHouses.add(fakeHome2);

        when(homeService.findAllDTO())
                .thenReturn(fakeHouses);

        // Act and Assert
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(fakeHome1.getAdress())))
                .andExpect(content().string(containsString(fakeHome2.getAdress())))
                .andExpect(content().string(not(containsString("FakeAdress"))));
    }

    @Test
    public void addHome_BadHome_NoAttemptionToAdd() throws Exception {
        // Arrange
        HomeDTO fakeHome = new HomeDTO();
        fakeHome.setId(1);
        fakeHome.setAdress("Adress1");
        fakeHome.setStageCount(-1);

        // Act and Assert
        mockMvc.perform(
                    post("/home")
                            .flashAttr(HomeController.NEW_HOME, fakeHome))
                .andDo(print())
                .andExpect(status().isOk());

        verify(homeService, times(0)).insertDTO(any());
    }

    @Test
    public void addHome_GoodHome_OneAttemptionToAdd() throws Exception {
        // Arrange
        HomeDTO fakeHome = new HomeDTO();
        fakeHome.setId(1);
        fakeHome.setAdress("Adress1");
        fakeHome.setStageCount(228);

        // Act and Assert
        mockMvc.perform(
                post("/home")
                        .flashAttr(HomeController.NEW_HOME, fakeHome))
                .andDo(print())
                .andExpect(status().isOk());

        verify(homeService, times(1)).insertDTO(any());
    }
}
