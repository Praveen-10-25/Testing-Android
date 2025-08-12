package before.after.contexttesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MessageUtilsTest {

    companion object {
        private lateinit var appContext: Context

        @JvmStatic
        @BeforeClass
        fun setupClass() {

            appContext = ApplicationProvider.getApplicationContext()
            println("BeforeClass: Context initialized")
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            println("AfterClass: Cleaning up resources")
        }
    }

    @Test
    fun testGetWelcomeMessage() {
        val message = MessageUtils.getWelcomeMessage(appContext)
        assertThat(message).isEqualTo("Welcome to the app!")
    }

    @Test
    fun testShowToastDoesNotCrash() {

        MessageUtils.showToast(appContext, "Hello Test!")
        assertThat(true).isTrue()
    }
}



