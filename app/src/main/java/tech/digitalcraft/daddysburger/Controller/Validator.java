package tech.digitalcraft.daddysburger.Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pattern , pattern1;
    private Matcher matcher;

    private static final String USERNAME_PATTERN = "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    public Validator(){
        pattern = Pattern.compile(USERNAME_PATTERN);
        pattern1 = Pattern.compile(PASSWORD_PATTERN);

    }

    /**
     * Validate username with regular expression
     * @param username username for validation
     * @return true valid username, false invalid username
     */
    public boolean validateUsername(final String username){

        matcher = pattern.matcher(username);
        return matcher.matches();

    }

    public boolean validatePassword(final String password){

        matcher = pattern1.matcher(password);
        return matcher.matches();

    }
}
