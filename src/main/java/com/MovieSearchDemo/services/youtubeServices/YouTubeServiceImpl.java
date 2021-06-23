package com.MovieSearchDemo.services.youtubeServices;
import com.MovieSearchDemo.data.models.MovieClip;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class YouTubeServiceImpl implements YouTubeService{
    @Value("${API_KEY}")
    String apiKeY;

    private static final long MAX_SEARCH_RESULTS = 7;
    @Override
    public List<MovieClip> fetchVideosByQuery(String queryTerm) {

        List<MovieClip> videos = new ArrayList<MovieClip>();
        try {
            //instantiate youtube object
            YouTube youtube = getYouTube();

            //define what info we want to get
            YouTube.Search.List search = youtube.search().list("id,snippet");

            //set our credentials
//            TODO add api key tpo environment variables in application.properties file

            search.setKey(apiKeY);

            //set the search term
            search.setQ(queryTerm);

            //we only want video results
            search.setType("video");

            //set the fields that we're going to use
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)");

            //set the max results
            search.setMaxResults(MAX_SEARCH_RESULTS);

            log.info("search object exists and is -->{}", search);

            DateFormat df = new SimpleDateFormat("MMM dd, yyyy");

            //perform the search and parse the results
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult result : searchResultList) {
                    MovieClip video = new MovieClip();
                    video.setTitle(result.getSnippet().getTitle());
                    video.setUrl(buildVideoUrl(result.getId().getVideoId()));
                    video.setThumbnailUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
                    video.setDescription(result.getSnippet().getDescription());
                    //parse the date
                    DateTime dateTime = result.getSnippet().getPublishedAt();
                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    video.setPublishDate(dateString);
                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    private String buildVideoUrl(String videoId){
        StringBuilder builder;
        builder = new StringBuilder();
        builder.append("https://www.youtube.com/watch?v=");
        builder.append(videoId);
//        String iframeQuery = "?autoplay&mute=1&loop=1&control=1";
//        builder.append(iframeQuery);
        return builder.toString();
    }

    private YouTube getYouTube(){
        return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),(request) -> {}).setApplicationName("youtube-spring-boot-demo").build();
    }

//    public static void main(String[] args) {
//        YouTubeServiceImpl result = new YouTubeServiceImpl();
//        result.fetchVideosByQuery("bill maher");
//        try {
//            result.getYouTube().search().list("avengers");
//            log.info("result --> {}",result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
