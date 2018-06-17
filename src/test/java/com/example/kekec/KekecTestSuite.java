package com.example.kekec;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)


@Suite.SuiteClasses({
        AddCandidateTest.class,
        AdditionalSpendingExtraLessonTest.class,
        BadInputTest.class,
        CandidateLessonsOverviewTest.class,
        DrivingLessonsTest.class,
        FindCandidateParameterizedTest.class,
        FindNonExistingCandidate.class,
        InstallmentPaymentTest.class,
        NegativeInstallmentPaymentTest.class,
        OtherAdditionalSpendingTest.class,
        UsersInDebtTest.class,
        UpdateCandidateTest.class,
        RemoveCandidateTest.class
})

public class KekecTestSuite {

}
