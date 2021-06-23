package com.MovieSearchDemo.services.youtubeServices;

import com.MovieSearchDemo.data.models.MovieClip;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class YouTubeServiceImplTest {
    @Mock
    YouTube youTube;

    @InjectMocks
    YouTubeServiceImpl youTubeServiceImpl;

    MovieClip testVideo;
    @BeforeEach
    void setUp() {
        testVideo = new MovieClip();
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        
    }

    @Test
    void fetchVideosByQuery() throws IOException {
//        when(youTubeServiceImpl.fetchVideosByQuery("an example")).thenReturn((List<MovieClip>) testVideo);
        youTubeServiceImpl.fetchVideosByQuery("another example");
        verify(youTube,times(1)).search().list("").execute().getItems();
//        verify(youTubeServiceImpl,times(1)).fetchVideosByQuery("another example");
    }
}