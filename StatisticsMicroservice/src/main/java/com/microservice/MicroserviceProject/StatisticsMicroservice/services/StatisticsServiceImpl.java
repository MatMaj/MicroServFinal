package com.microservice.MicroserviceProject.StatisticsMicroservice.services;

import com.microservice.MicroserviceProject.StatisticsMicroservice.daos.StatisticsDao;
import com.microservice.MicroserviceProject.StatisticsMicroservice.entities.Statistics;
import com.microservice.MicroserviceProject.StatisticsMicroservice.utilities.JsonResponseBody;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
@Log
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsDao statisticsDao;

    @Override
    public List<Statistics> getStatistics(String jwt, String email){

        List<LinkedHashMap> todos = getNewDataFromToDoMicroservice(jwt);

        String statisticsDescr = "No statistics available";
        if(todos !=null && todos.size()>0){
            int lowPriorityTodos = 0;
            int highPriorityTodos = 0;
            for(int i=0; i<todos.size(); i++){
                LinkedHashMap todo = todos.get(i);
                String priority = (String) todo.get("priority");
                if("low".equals(priority)) lowPriorityTodos++;
                if("high".equals(priority)) highPriorityTodos++;

            }
            statisticsDescr = "You have <b>" + lowPriorityTodos + " low priority</b> ToDos and <b>" + highPriorityTodos + " high priority</b> ToDo";
        }

        //3. save the new statistic into statisticdb if a day has passed from the last update
        List<Statistics> statistics = statisticsDao.getLastStatistics(email);
        if(statistics.size()>0){
            Date now = new Date();
            long diff = now.getTime() - statistics.get(0).getDate().getTime();

            long days = diff/(24 * 60 * 60 * 1000);
            if(days>1){
                statistics.add(statisticsDao.save(new Statistics(null, statisticsDescr, new Date(), email)));
            }
        }
        return statistics;
    }


    private List<LinkedHashMap> getNewDataFromToDoMicroservice(String jwt){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("jwt", jwt);
        HttpEntity<?> request = new HttpEntity(String.class, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonResponseBody> responseEntity = restTemplate.exchange("http://localhost:8383/showMicro", HttpMethod.POST, request, JsonResponseBody.class);

        List<LinkedHashMap> todoList = (List) responseEntity.getBody().getResponse();

        return todoList;
    }

}