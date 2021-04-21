

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.lambton.bikr.activity.HomeActivity
import com.lambton.bikr.signup.SignupActivity

fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()


//fun Context.startHomeActivity() =
//    Intent(this, HomeActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }

//fun Context.startLoginActivity() =
//    Intent(this, FireBaseLoginActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }
fun hideSoftKeyBoard(context: Context, view: View) {
    try {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } catch (e: Exception) {
        // TODO: handle exception
        e.printStackTrace()
    }

}

fun fromHtml(source: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(source)
    }
}
fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
fun Context.startSignupActivity() =
    Intent(this, SignupActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startNearByActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

//fun Context.startProfileActivity(uid:String) =
//    Intent(this, ProfileActivity::class.java).also {
//        it.putExtra("userid",uid)
//     ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }
//fun Context.startSeekProfileActivity(uid:String, post:Emp_post) =
//    Intent(this, SeekerProfileActivity::class.java).also {
//        it.putExtra("userid",uid)
//        it.putExtra("post",post)
//        ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }
//fun Context.startMsgActivity(post_id:String,sender_id:String,other_id:String) =
//    Intent(this, MessagesActivity::class.java).also {
//
//        it.putExtra("post_id",post_id)
//        it.putExtra("sender_id",sender_id)
//        it.putExtra("other_id",other_id)
//        ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(it)
//    }


//fun Context.startAddDBActivity() =
//    Intent(this, PostActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivities(arrayOf(it))
//    }

//fun Context.startStorageActivity() =
//    Intent(this, StorageActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivities(arrayOf(it))
//    }
//fun Context.startEmpPostsActivity() =
//    Intent(this, EmpPostsActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivities(arrayOf(it))
//    }
//
//fun Context.startPostsRepliesActivity() =
//    Intent(this, PostsResponseActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivities(arrayOf(it))
//    }
//
//
//fun Context.startUserListActivity() =
//    Intent(this, UserListActivity::class.java).also {
//        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivities(arrayOf(it))
//    }


