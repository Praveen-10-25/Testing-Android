package before.after.contexttesting

import android.content.Context
import android.widget.Toast

object MessageUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getWelcomeMessage(context: Context): String {
        return context.getString(R.string.welcome_message)
    }
}
