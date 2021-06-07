package covid19api.facade.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.util.Collection;

@FeignClient(name = "covid19api", url = "https://api.covid19api.com/")
public interface CovidApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/country/{countrySlug}")
    Collection<DayInfoRecord> byCountry(@PathVariable("countrySlug") String countrySlug,
                                        @RequestParam ZonedDateTime from,
                                        @RequestParam ZonedDateTime to);
}
