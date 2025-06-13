package com.jagl.critiq.core.common

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.jagl.critiq.core.local.AppDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class CriticQAndroidTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var db: AppDatabase

    protected lateinit var context: Context

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        db.clearAllTables()
    }

    @After
    open fun tearDown() {
        db.close()
    }
}

