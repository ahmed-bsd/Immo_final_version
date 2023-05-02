package tn.esprit.spring.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;
    @PostConstruct
    public void init() {
        Twilio.init(
                getTwilioAccountSid(),
                getTwilioAuthToken()
        );
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public String getTwilioAuthToken() {
        return twilioAuthToken;
    }

}
