//place name record class with fields for all fields in csv file
//LBNSEB002

public record PlaceNameEntry(
        String id,
        String placeName,
        String municipality,
        String province,
        Integer population
) {}
