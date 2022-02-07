package com.example.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultSoal {
    List<HasilLatihanModel> soalSudahDikerjakan;
    List<SoalModel> soalBelumDikerjakan;
    double score;
}
