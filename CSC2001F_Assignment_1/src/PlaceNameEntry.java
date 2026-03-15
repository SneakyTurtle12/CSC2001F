//place name record class with fields for all fields in csv file
//LBNSEB002

public record PlaceNameEntry(
        String id,
        String placeName,
        String municipality,
        String province,
        Integer population
) implements Comparable<PlaceNameEntry> {
    @Override
    public int compareTo(PlaceNameEntry other){
        return this.placeName().compareTo(other.placeName());
    }
    public String toString(){
        return this.id() + "," + this.placeName() + "," + this.municipality()+ "," + this.province() + "," + this.population();
    }
}

