package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.security.Principal;


@Service
public class AccountService {

    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    public AccountService (JdbcUserDao userDao, JdbcAccountDao accountDao)
    {

        this.userDao = userDao;
        this.accountDao = accountDao;
    }
    public BigDecimal getBalance(Principal userInfo)
    {
        BigDecimal balance = new BigDecimal ("500.00");

        String username = userInfo.getName();
        User user = userDao.findByUsername(username);
        Long userId = user.getId();
        balance = accountDao.getBalance(userId);
        //System.out.println(userInfo.getName());
        return balance;
    }
/*
    private Meme[] getMemes() {

        // add code to call get_memes endpoint
        try {
            MemeApiGetResponse response = restTemplate.getForObject(API_BASE_URL + "get_memes", MemeApiGetResponse.class);

            return response.getData().getMemes();
        } catch (RestClientResponseException ex) {
            BasicLogger.log("API Error: " + ex.getRawStatusCode() + " " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            BasicLogger.log("API Error: " + ex.getMessage());
        }
        return new Meme[] {};
    }*/

    /**
     * Uses caption_image endpoint to create meme and return URL
     * @param info
     * @return String
     */
    /*public String getBalance(CreateMemeInfo info) {

        CaptionedMeme newMeme = new CaptionedMeme();

        newMeme.setTemplateId(info.getMemeId());
        newMeme.setText0(info.getCaption());
        newMeme.setUsername(API_USERNAME);
        newMeme.setPassword(API_PASSWORD);

        MultiValueMap<String, String> postData = memeUtils.formDataFromCaptionedMeme(newMeme);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(postData, httpHeaders);


        MemeApiCreateResponse response = restTemplate.postForObject(API_BASE_URL + "caption_image", entity, MemeApiCreateResponse.class);

        return response.getData().getUrl();
    }*/




}
