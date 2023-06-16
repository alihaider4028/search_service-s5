package com.anushka.search_services5.Services;


import com.anushka.search_services5.Response.ApiResponse;
import com.anushka.search_services5.entity.SearchCriteria;

public interface SearchService {

    public ApiResponse<?> searchByLocation(int locationId);
    public ApiResponse<?> searchEventByCapacityLessThen(int capacity);
    public ApiResponse<?> searchEventByDate(String date);
    public  ApiResponse<?> searchEventByTitle(String title);
    public ApiResponse<?> searchEventByCapacityGreaterThen(int capacity);
    public ApiResponse<?> searchByEventType(int type);

    public ApiResponse<?> search(String keyword);
   public ApiResponse<?> searchEvents(String searchBy,String value);






}
