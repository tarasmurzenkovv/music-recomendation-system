package repository;

import configuration.database.DataBaseContextConfiguration;
import entities.Track;
import entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataBaseContextConfiguration.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    public void testSavingUser() {
        User user = new User("test_login", "test_email");
        Track track1 = new Track("track_name1",10.0,"artist_name1");
        Track track2 = new Track("track_name2",10.0,"artist_name2");
        user.addTrack(track1);
        user.addTrack(track2);
        userRepository.save(user);
        assertEquals(user, new User("test_login", "test_email"));
    }
}
