package com.harshalshewale.apiautomationtestingproject.testsuite;

import org.junit.jupiter.api.Nested;

import com.harshalshewale.apiautomationtestingframework.test.BaseTestSuite;
import com.harshalshewale.apiautomationtestingproject.test.MyFirstTest;
import com.harshalshewale.apiautomationtestingproject.test.MySecondTest;

public class SmokeTestSuite implements BaseTestSuite{

	@Nested
    class Test1 extends MyFirstTest{};
    @Nested
    class Test2 extends MySecondTest{};

}
