package com.example.demo.service;

import java.io.IOException;

import com.example.demo.model.CompilerModel;
import com.example.demo.repository.CompilerInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class CompilerService {

    CompilerInterface compiler;
    
    public CompilerService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        compiler = retrofit.create(CompilerInterface.class);        
    }

    public CompilerModel getOutput(CompilerModel data){
        Call<CompilerModel> call = compiler.getOutput(data);
        Response<CompilerModel> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response.body();
    }
}
