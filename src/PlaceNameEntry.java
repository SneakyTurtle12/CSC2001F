//place name record class with fields for all fields in csv file
//LBNSEB002

public record PlaceNameEntry(
        //*Record class for use in entire program, implements comparable and toString output gives output in csv format*/
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

