package covid19api.facade.api;

import covid19api.facade.api.client.CovidApiClient;
import covid19api.facade.api.client.DayInfoRecord;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ApiCallTestController {
    private final static Logger logger = LoggerFactory.getLogger(ApiCallTestController.class);
    private final CovidApiClient covidApiClient;

    public ApiCallTestController(CovidApiClient covidApiClient) {
        this.covidApiClient = covidApiClient;
    }


    @GetMapping("/country/{countrySlug}")
    public Collection<DayInfoRecord> byCountry(@PathVariable @Length(min = 3) String countrySlug,

                                               @RequestParam(required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       ZonedDateTime from,

                                               @RequestParam(required = false)
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                       ZonedDateTime to) {
        if (from != null && to != null) {
            return covidApiClient.byCountry(countrySlug, from, to);
        } else {
            logger.info("No dates interval defined. Requesting data for last year.");
            final ZonedDateTime now = ZonedDateTime.now();
            return covidApiClient.byCountry(countrySlug, now.minusYears(1), now);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constrainExceptionHandle(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
