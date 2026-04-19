package com.fagundes.myshowlist

import com.fagundes.myshowlist.feat.login.ui.LoginScreenTest
import com.fagundes.myshowlist.feat.home.ui.HomeScreenTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    LoginScreenTest::class,
    HomeScreenTest::class
)
class InstrumentedTestSuite
