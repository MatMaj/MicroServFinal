package com.microservice.MicroserviceProject.StatisticsMicroservice.services;

import com.microservice.MicroserviceProject.StatisticsMicroservice.entities.Statistics;

import java.util.List;

public interface StatisticsService {

    List<Statistics> getStatistics(String jwt, String email);
}