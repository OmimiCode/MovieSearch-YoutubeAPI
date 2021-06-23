package com.MovieSearchDemo.web.controllers;

import com.MovieSearchDemo.data.models.MovieClip;
import com.MovieSearchDemo.data.models.MovieSearchCriteria;

import com.MovieSearchDemo.services.youtubeServices.YouTubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class MovieSearchDemoController {

    @Autowired
    YouTubeService youTubeServiceImpl;




    @RequestMapping(value = "/index", method=RequestMethod.GET)
    public String youtubeDemo(Model model) {
        //instantiate an empty address object
        MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria();
        //put the object in the model
        model.addAttribute("movieSearchCriteria", movieSearchCriteria);
        //get out
        return "index";
    }

    //redirect to demo if user hits the root/index page
    @RequestMapping ("/")
    public String home(Model model) {
        return "redirect:index";
    }



    @RequestMapping(value = "/index", method=RequestMethod.POST)
    public String formSubmit(@Valid MovieSearchCriteria movieSearchCriteria, BindingResult bindingResult, Model model) {
        //check for errors
        if (bindingResult.hasErrors()) {
            return "index";
        }

        //get the list of YouTube videos that match the search term
        List<MovieClip> videos = youTubeServiceImpl.fetchVideosByQuery(movieSearchCriteria.getQueryTerm());

        if (videos != null && videos.size() > 0) {
            model.addAttribute("numberOfVideos", videos.size());
        } else {
            model.addAttribute("numberOfVideos", 0);
        }

        //put it in the model
        model.addAttribute("videos", videos);

        //add the criteria to the model as well
        model.addAttribute("movieSearchCriteria", movieSearchCriteria);

        //get out
        return "search-Results";
    }






}
