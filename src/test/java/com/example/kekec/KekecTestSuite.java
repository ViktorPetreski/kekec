package com.example.kekec;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)


@Suite.SuiteClasses({
        JUnitTest.class,
        UpdateCandidateTest.class,
        AdditionalSpendingExtraLessonTest.class,
        DrivingLessonsTest.class,
        FindCandidateTest.class
})

public class KekecTestSuite {

}
