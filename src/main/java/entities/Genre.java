package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "genre")
class Genre {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "genre")
    private String genreName;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<Track> tracks;

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return genreName != null ? genreName.equals(genre.genreName) : genre.genreName == null;

    }

    @Override
    public int hashCode() {
        return genreName != null ? genreName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreName='" + genreName + '\'' +
                '}';
    }
}
