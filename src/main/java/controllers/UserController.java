package controllers;

import entities.Track;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping("/playlist")
    public List<Track> formUserPlayList() {
        List<Track> tracks = new ArrayList<>();

        tracks.add(new Track(1L, "Track name 1"));
        tracks.add(new Track(2L, "Track name 2"));
        tracks.add(new Track(3L, "Track name 2"));

        return tracks;
    }
}
