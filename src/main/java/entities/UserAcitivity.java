package entities;

import javax.persistence.*;

@Entity
@Table(name = "activity")
class UserAcitivity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "track_likes")
    private Long likes;

    @Column(name = "track_downloads")
    private Long downloads;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id", foreignKey = @ForeignKey(name = "track_id"))
    private Track track;

    public UserAcitivity() {
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAcitivity that = (UserAcitivity) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return track != null ? track.equals(that.track) : that.track == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (track != null ? track.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAcitivity{" +
                "likes=" + likes +
                ", downloads=" + downloads +
                ", user=" + user +
                ", track=" + track +
                '}';
    }
}
