package com.adamco.ecommerceapp
//
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.adamco.snackbardemo.databinding.ActivityMainBinding
//import com.google.android.material.snackbar.Snackbar
//
//class SnackBar : AppCompatActivity() {
//    private lateinit var binding : ActivitySnackBarBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initViews()
//    }
//
//    private fun initViews() {
//        with(binding){
//            btnShowSnackBar.setOnClickListener {
//                showSnackBar(resources.getString(R.string.no_action_message))
//            }
//            btnShowSnackBarWithAction.setOnClickListener {
//                showSnackBarWithAction()
//            }
//        }
//    }
//
//
//    private fun isWifiConnected() : Boolean{
//        return (0..1).random() ==1
//    }
//
//    private fun showSnackBarWithAction() {
//        // this is the initial snackbar message that you get which provides you with the action
//        // that you define below
//        val snackBar = Snackbar.make(
//            binding.main,
//            resources.getString(R.string.with_action_message),
//            Snackbar.LENGTH_INDEFINITE
//        )
//
//        snackBar.setAction("Retry") {
//            if (isWifiConnected()) {
//                showSnackBar(resources.getString(R.string.internet_available))
//            } else {
//                showSnackBar(resources.getString(R.string.no_internet))
//            }
//        }
//
//        snackBar.show()
//    }
//
//    // function to create the snackbar object
//    private fun showSnackBar(message: String) {
//        Snackbar.make(
//            binding.main,
//            message,
//            Snackbar.LENGTH_LONG
//        ).show()
//    }
//}