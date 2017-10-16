package test.health.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestAppFirstController {

    protected Logger logger = LoggerFactory.getLogger(TestAppFirstController.class);

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    @Value("${test2.application.endpoint}")
    private String secondTestAppEndpoint;

    @RequestMapping(value = "/firstTestApp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String testApp() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(this.secondTestAppEndpoint, String.class);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            logger.error(
                    "Response contains an error.. Unable to invoke second test app endpoint  " + responseEntity.toString());
            return null;
        } else {
            logger.debug("response : " + responseEntity.toString());
        }

        //return "TestApp1 : " + responseEntity.toString();
        return "TestApp1 : " + responseEntity.getBody().toString();
    }

}