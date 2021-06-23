package com.MovieSearchDemo.data.models;

import lombok.Data;

@Data
public class MovieClip {
    private String title;
    private String url;
    private String thumbnailUrl;
    private String publishDate;
    private String description;
}
