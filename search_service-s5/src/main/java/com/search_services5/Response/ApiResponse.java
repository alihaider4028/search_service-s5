package com.anushka.search_services5.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString

public class ApiResponse<T> {
    int statusCode;
    String statusDescription;
    T result;
}
