package org.acme.accountregistry.service;

import lombok.NoArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import static lombok.AccessLevel.PRIVATE;
import static org.passay.DigestDictionaryRule.ERROR_CODE;

@NoArgsConstructor(access = PRIVATE)
final class PasswordUtils {

    private static final PasswordGenerator GEN = new PasswordGenerator();
    private static final CharacterRule SPECIAL_CHARS_RULE = specialCharsRule();
    private static final CharacterRule LOWER_CASE_RULE = lowerCaseRule();
    private static final CharacterRule UPPER_CASE_RULE = upperCaseRule();
    private static final CharacterRule DIGIT_RULE = digitRule();

    private static CharacterRule specialCharsRule() {
        final var specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        return new CharacterRule(specialChars, 2);
    }

    private static CharacterRule lowerCaseRule() {
        return new CharacterRule(EnglishCharacterData.LowerCase, 2);
    }

    private static CharacterRule upperCaseRule() {
        return new CharacterRule(EnglishCharacterData.UpperCase, 2);
    }

    private static CharacterRule digitRule() {
        return new CharacterRule(EnglishCharacterData.Digit, 2);
    }

    public static String generateRandomPassword() {
        return GEN.generatePassword(10, SPECIAL_CHARS_RULE, LOWER_CASE_RULE,
                                    UPPER_CASE_RULE, DIGIT_RULE);
    }
}
