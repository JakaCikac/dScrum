package si.fri.tpo.gwt.server.impl.login;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import si.fri.tpo.gwt.client.components.Pair;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nanorax on 04/04/14.
 */
public class LoginServiceImpl {

    public static Pair<PersonDTO, String> performUserLogin(String username, String passwordMD5) {
        Person person = ProxyManager.getPersonProxy().findPersonByUsername(username);

        if (person == null)
            return Pair.of(null, "Uporabnik s takšnim uporabniškim imenom ne obstaja!");

        List<LoginLog> personLogins = person.getLoginLog();
        DateTimeZone zone = DateTimeZone.getDefault();
        DateTime dt = new DateTime(zone);
        DateTime dtMin30 = dt.minusMinutes(ServerConstants.LOCK_TIME_MINS);


        // check failed logins for last 10 minutes
        int countAttemps = 0;
        for (LoginLog loginLog : personLogins) {
            if (!loginLog.isSuccess() && dtMin30.isBefore(loginLog.getTimeLogin().getTime()))
                countAttemps++;
        }

        // lock username
        if (countAttemps >= ServerConstants.MAX_LOGIN_ATTEMPTS)
            return Pair.of(null, "Sistem je zaklenjen! Prijavili se boste lahko čez cca " + ServerConstants.LOCK_TIME_MINS + " minut.");

        // verify password
        final boolean successAttemp = person.getPassword().equals(passwordMD5);

        //write login attemp to database
        LoginLog log = new LoginLog();
        log.setSuccess(successAttemp);
        log.setTimeLogin(new Timestamp(dt.getMillis()));
        ProxyManager.getLoginLogProxy().create(log);

        if (personLogins == null)
            personLogins = new ArrayList<LoginLog>();

        personLogins.add(log);
        try {
            ProxyManager.getPersonProxy().edit(person);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!successAttemp)
            return Pair.of(null, "Geslo je napačno!");

        return Pair.of(DTOfiller.fillPersonDTO(person), "success");
    }

}
