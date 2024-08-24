package org.acme.accountregistry.domain.service;

import lombok.NoArgsConstructor;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.PasswordGenerator;

import static lombok.AccessLevel.PRIVATE;
import static org.passay.DigestDictionaryRule.ERROR_CODE;
import static org.passay.EnglishCharacterData.Digit;
import static org.passay.EnglishCharacterData.LowerCase;
import static org.passay.EnglishCharacterData.UpperCase;

@NoArgsConstructor(access = PRIVATE)
final class PasswordUtils {

    private static final PasswordGenerator GEN = new PasswordGenerator();
    private static final CharacterRule SPECIAL_CHARS_RULE = specialCharsRule();
    private static final CharacterRule LOWER_CASE_RULE = new CharacterRule(LowerCase, 2);
    private static final CharacterRule UPPER_CASE_RULE = new CharacterRule(UpperCase, 2);
    private static final CharacterRule DIGIT_RULE = new CharacterRule(Digit, 2);

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

    static String generateRandomPassword() {
        return GEN.generatePassword(10, SPECIAL_CHARS_RULE, LOWER_CASE_RULE,
                                    UPPER_CASE_RULE, DIGIT_RULE);
    }
}
