package com.vladooha.controller;

import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.service.ProfileService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileContoller.class)
public class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    public void getProfilesInDB_2Profiles_2ProfilesData() throws Exception {
        // Arrange
        ProfileDTO fakeProfile1 = new ProfileDTO();
        fakeProfile1.setName("Name1");
        fakeProfile1.setSurname("Surname1");

        ProfileDTO fakeProfile2 = new ProfileDTO();
        fakeProfile2.setName("Name2");
        fakeProfile2.setSurname("Surname2");

        List<ProfileDTO> fakeProfiles = new ArrayList<>();
        fakeProfiles.add(fakeProfile1);
        fakeProfiles.add(fakeProfile2);

        when(profileService.findAllDTO())
                .thenReturn(fakeProfiles);

        // Act and Assert
        mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(fakeProfile1.getName())))
                .andExpect(content().string(containsString(fakeProfile1.getSurname())))
                .andExpect(content().string(containsString(fakeProfile2.getName())))
                .andExpect(content().string(containsString(fakeProfile2.getSurname())))
                .andExpect(content().string(not(containsString("Random data"))));
    }

    @Test
    public void addProfile_WrongProfile_NoAttemptionToAdd() throws Exception {
        // Arrange
        ProfileDTO fakeProfile = new ProfileDTO();
        fakeProfile.setId(1);
        fakeProfile.setName(null);
        fakeProfile.setSurname(null);

        // Act and Assert
        mockMvc.perform(
                post("/profile")
                        .flashAttr(ProfileContoller.NEW_PROFILE, fakeProfile))
                .andDo(print())
                .andExpect(status().isOk());

        verify(profileService, times(0)).insertDTO(any());
    }

    @Test
    public void addProfile_GoodProfile_OneAttemptionToAdd() throws Exception {
        // Arrange
        ProfileDTO fakeProfile = new ProfileDTO();
        fakeProfile.setId(1);
        fakeProfile.setName("Good");
        fakeProfile.setSurname("Profile");

        // Act and Assert
        mockMvc.perform(
                post("/profile")
                        .flashAttr(ProfileContoller.NEW_PROFILE, fakeProfile))
                .andDo(print())
                .andExpect(status().isOk());

        verify(profileService, times(1)).insertDTO(any());
    }
}
