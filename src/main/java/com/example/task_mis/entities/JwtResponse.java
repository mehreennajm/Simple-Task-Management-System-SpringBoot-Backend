package com.example.task_mis.entities;



public class JwtResponse {

    private User user;
    private String jwtToken;

    public JwtResponse(String jwtToken) {

        this.jwtToken = jwtToken;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
