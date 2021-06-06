package covid19api.facade.api;

import covid19api.facade.api.client.CovidApiClient;
import covid19api.facade.api.client.DayInfoRecord;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ApiCallTestController {
    private final CovidApiClient covidApiClient;

    public ApiCallTestController(CovidApiClient covidApiClient) {
        this.covidApiClient = covidApiClient;
    }


    @GetMapping("/country/{countrySlug}")
    public Collection<DayInfoRecord> byCountry(@PathVariable @Length(min = 3) String countrySlug) {
        return covidApiClient.byCountry(countrySlug);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constrainExceptionHandle(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
