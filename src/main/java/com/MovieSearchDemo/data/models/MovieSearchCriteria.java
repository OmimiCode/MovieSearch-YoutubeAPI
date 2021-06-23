package com.MovieSearchDemo.data.models;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class MovieSearchCriteria {
    @Size(min=5, max=64, message="Search term must be between 5 and 64 characters")
    private String queryTerm;
}
