package com.semihunaldi.intellij.plugin.backend.util;

import java.util.Random;

public class PasswordGenerator
{
    private static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";

    private int maximumLength;
    private int minimumLength;
    private int minimumCapitalLetter;
    private int minimumNumber;
    private int minimumSmallLetter;

    public PasswordGenerator(int _maximumLength, int _minimumLength, int _minimumCapitalLetter, int _minimumNumber, int _minimumSmallLetter)
    {
        this.maximumLength = _maximumLength;
        this.minimumLength = _minimumLength;
        this.minimumCapitalLetter = _minimumCapitalLetter;
        this.minimumNumber = _minimumNumber;
        this.minimumSmallLetter = _minimumSmallLetter;
    }

    public PasswordGenerator(PasswordGenerationCriteria passwordGenerationCriteria)
    {
        this.maximumLength = passwordGenerationCriteria.getMaximumLength();
        this.minimumLength = passwordGenerationCriteria.getMinimumLength();
        this.minimumCapitalLetter = passwordGenerationCriteria.getMinimumCapitalLetter();
        this.minimumNumber = passwordGenerationCriteria.getMinimumNumber();
        this.minimumSmallLetter = passwordGenerationCriteria.getMinimumSmallLetter();
    }

    public String generate()
    {
        checkCriterias();
        Random randomHelper = new Random();
        int passwordLength = randomHelper.nextInt(maximumLength - minimumLength + 1) + minimumLength;
        char[] password = new char[passwordLength];
        fillPasswordChars(randomHelper, passwordLength, password, CAPITAL_LETTERS, minimumCapitalLetter);
        fillPasswordChars(randomHelper, passwordLength, password, NUMBERS, minimumNumber);
        fillPasswordChars(randomHelper, passwordLength, password, SMALL_LETTERS, minimumSmallLetter);
        fillRemainingPasswordChars(randomHelper, passwordLength, password);
        return new String(password);
    }

    private void fillPasswordChars(Random randomHelper, int passwordLength, char[] password, String charsToUse, int minimumCount)
    {
        int index;
        for (int i = 0; i < minimumCount; i++)
        {
            index = getNextIndex(randomHelper, passwordLength, password);
            password[index] = charsToUse.charAt(randomHelper.nextInt(charsToUse.length()));
        }
    }

    private int getNextIndex(Random randomHelper, int length, char[] password)
    {
        int index;
        while (password[index = randomHelper.nextInt(length)] != 0) {}
        return index;
    }

    private void fillRemainingPasswordChars(Random randomHelper, int passwordLength, char[] password)
    {
        for (int i = 0; i < passwordLength; i++)
        {
            if (password[i] == 0)
            {
                password[i] = SMALL_LETTERS.charAt(randomHelper.nextInt(SMALL_LETTERS.length()));
            }
        }
    }

    private void checkCriterias()
    {
        if(maximumLength < minimumLength)
        {
            throw new IllegalArgumentException("maximumLength must be equal or greater than minimumLength");
        }
        if(minimumCapitalLetter + minimumNumber + minimumSmallLetter > minimumLength)
        {
            throw new IllegalArgumentException("minimumLength must be greater or equal than the sum of minimumCapitalLetter, minimumNumber and minimumSmallLetter");
        }
    }

    public enum PasswordGenerationCriteria
    {
        //TODO anlamlı isimler ile anlaşılabilir criterialar tanımlamak daha güzel olabilir.
        BASIC_PASSWORD(6,6,0,0,1),
        MODERATE_PASSWORD(6,6,1,0,1),
        HARD_PASSWORD(6,6,1,1,1),
        MAX_LENGTH_7__MIN_LENGTH_7__MIN_CAPITAL_LETTER_0__MIN_NUMBER_7__MIN_SMALL_LETTER_0(7,7,0,7,0),
        MAX_LENGTH_6__MIN_LENGTH_6__MIN_CAPITAL_LETTER_1__MIN_NUMBER_1__MIN_SMALL_LETTER_3(6,6,1,1,3),
        MAX_LENGTH_7__MIN_LENGTH_7__MIN_CAPITAL_LETTER_0__MIN_NUMBER_4__MIN_SMALL_LETTER_0(7,7,0,4,0),
        MAX_LENGTH_6__MIN_LENGTH_6__MIN_CAPITAL_LETTER_0__MIN_NUMBER_6__MIN_SMALL_LETTER_0(6,6,0,6,0),
        MAX_LENGTH_8__MIN_LENGTH_8__MIN_CAPITAL_LETTER_1__MIN_NUMBER_1__MIN_SMALL_LETTER_1(8,8,1,1,1),
        MAX_LENGTH_8__MIN_LENGTH_8__MIN_CAPITAL_LETTER_3__MIN_NUMBER_2__MIN_SMALL_LETTER_3(8,8,3,2,3);

        private int maximumLength;
        private int minimumLength;
        private int minimumCapitalLetter;
        private int minimumNumber;
        private int minimumSmallLetter;

        PasswordGenerationCriteria(int _maximumLength, int _minimumLength, int _minimumCapitalLetter, int _minimumNumber, int _minimumSmallLetter)
        {
            this.maximumLength = _maximumLength;
            this.minimumLength = _minimumLength;
            this.minimumCapitalLetter = _minimumCapitalLetter;
            this.minimumNumber = _minimumNumber;
            this.minimumSmallLetter = _minimumSmallLetter;
        }

        public int getMaximumLength()
        {
            return maximumLength;
        }

        public int getMinimumLength()
        {
            return minimumLength;
        }

        public int getMinimumCapitalLetter()
        {
            return minimumCapitalLetter;
        }

        public int getMinimumNumber()
        {
            return minimumNumber;
        }

        public int getMinimumSmallLetter()
        {
            return minimumSmallLetter;
        }
    }
}
