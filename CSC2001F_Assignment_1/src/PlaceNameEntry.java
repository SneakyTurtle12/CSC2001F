//place name record class with fields for all fields in csv file
//LBNSEB002

public record PlaceNameEntry(
        String placeName,
        String municipality,
        String id,
        String province,
        Integer population
) {}
