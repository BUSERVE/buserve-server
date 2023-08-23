package com.example.buserve.src.login.client;


import com.example.buserve.src.user.User;

public interface ClientProxy {

    User getUserData(String accessToken);
}
