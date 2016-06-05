package core.mp3.dtos;

import java.util.Arrays;

public class Mp3FileInformationDto {
    private double[] frequencies;
    private String genre;
    private String trackName;
    private double like;

    public Mp3FileInformationDto() {
    }

    public double[] getFrequencies() {
        return frequencies;
    }

    public void setFrequencies(double[] frequencies) {
        this.frequencies = frequencies;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public double getLike() {
        return like;
    }

    public void setLike(double like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mp3FileInformationDto that = (Mp3FileInformationDto) o;

        if (Double.compare(that.like, like) != 0) return false;
        if (!Arrays.equals(frequencies, that.frequencies)) return false;
        if (genre != null ? !genre.equals(that.genre) : that.genre != null) return false;
        return trackName != null ? trackName.equals(that.trackName) : that.trackName == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Arrays.hashCode(frequencies);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (trackName != null ? trackName.hashCode() : 0);
        temp = Double.doubleToLongBits(like);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Mp3FileInformationDto{" +
                "frequencies=" + Arrays.toString(frequencies) +
                ", genre='" + genre + '\'' +
                ", trackName='" + trackName + '\'' +
                ", like=" + like +
                '}';
    }
}
