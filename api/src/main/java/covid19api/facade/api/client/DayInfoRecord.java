package covid19api.facade.api.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public record DayInfoRecord(
        UUID id,
        int confirmed,
        int deaths,
        int recovered,
        int active,
        ZonedDateTime date
) {
    @JsonCreator
    public DayInfoRecord(@JsonProperty("ID") UUID id,
                         @JsonProperty("Confirmed") int confirmed,
                         @JsonProperty("Deaths") int deaths,
                         @JsonProperty("Recovered") int recovered,
                         @JsonProperty("Active") int active,
                         @JsonProperty("Date") ZonedDateTime date) {
        this.id = id;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.date = date;
    }
}
