package com.example.demo.repository;

import com.example.demo.model.CompilerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CompilerInterface {
    @POST("/")
    Call<CompilerModel> getOutput(@Body CompilerModel data);
}
