package util;

/**
 * Utility class for password-related operations
 * Note: Password validation is handled in AuthServiceImpl.validatePassword()
 * This class can be extended for additional password utilities if needed
 */
public class PasswordUtil {
    
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 128;
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    /**
     * Validates password strength
     * @param password the password to validate
     * @return true if password meets strength requirements
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> SPECIAL_CHARS.indexOf(ch) >= 0);
        
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial;
    }
    
    /**
     * Gets password strength requirements message
     * @return requirements message
     */
    public static String getPasswordRequirements() {
        return String.format(
            "Password must be between %d and %d characters and contain at least one uppercase letter, " +
            "one lowercase letter, one digit, and one special character (%s)",
            MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, SPECIAL_CHARS
        );
    }
}
