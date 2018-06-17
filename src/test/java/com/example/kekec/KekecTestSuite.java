package com.example.kekec;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)


@Suite.SuiteClasses({
        InstallmentPaymentTest.class,
        UpdateCandidateTest.class,
        AdditionalSpendingExtraLessonTest.class,
        DrivingLessonsTest.class,
        FindNonExistingCandidate.class
})

public class KekecTestSuite {

}
