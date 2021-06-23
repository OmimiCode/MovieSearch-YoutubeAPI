package com.MovieSearchDemo.services.youtubeServices;

import com.MovieSearchDemo.data.models.MovieClip;


import java.util.List;

public interface YouTubeService {
    List<MovieClip> fetchVideosByQuery(String queryTerm);
}
