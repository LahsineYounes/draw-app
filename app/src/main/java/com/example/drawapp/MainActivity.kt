package com.example.drawapp

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    private var mImageButtonCurrentPaint: ImageButton? = null // A variable for current color is picked from color pallet.

    var customProgressDialog: Dialog? = null

    // create an ActivityResultLauncher with MultiplePermissions since we are requesting both read and write
    private val requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val perMissionName = it.key
                val isGranted = it.value
                //if permission is granted show a toast and perform operation
                } else {
                    // Displaying another toast if permission is not granted and this time focus on Read external storage
                    //if (perMissionName == Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        /*
        initializes `mImageButtonCurrentPaint` to the second child of the `LinearLayout` identified by `R.id.ll_paint_colors` 
        and sets its drawable to `R.drawable.palet_pressed`.
        */
        val linearLayoutPaintColors = findViewById<LinearLayout>(R.id.ll_paint_colors)
        mImageButtonCurrentPaint = linearLayoutPaintColors[1] as ImageButton
        mImageButtonCurrentPaint?.setImageDrawable(
            ContextCompat.getDrawable(
                this, R.drawable.palet_pressed
            )
        )

        val ibBrush: ImageButton = findViewById(R.id.ib_brush)
        ibBrush.setOnClickListener {
            showBrushSizeChooserDialog()
        }
        
        val ibUndo: ImageButton = findViewById(R.id.ib_undo)
        ibUndo.setOnClickListener {
            // This is for undo recent stroke.
            drawingView?.onClickUndo()
        }
        
    }


    // Method is used to launch the dialog to select different brush sizes.
    private fun showBrushSizeChooserDialog() {
        
    }


    // Method is called when color is clicked from pallet_normal.
    fun paintClicked(view: View) {
        if (view !== mImageButtonCurrentPaint) {
            
        }
    }

    // We are calling this method to check the permission status
    private fun isReadStorageAllowed(): Boolean {

        val result = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        )

        return result == PackageManager.PERMISSION_GRANTED
    }


    private fun requestStoragePermission(){
        // Check if the permission was denied and show rationale
        if (){
            
        }
        else {
            
        }

    }

    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }


    
    private fun getBitmapFromView(view: View): Bitmap {
        /*
        This method creates a `Bitmap` object from a provided `View` object. 
        It is used to capture the current visual representation of the view.
        */
    }

    

    private suspend fun saveBitmapFile(mBitmap: Bitmap?):String{

        /*
        This `suspend` function (which can be called within a coroutine) saves a `Bitmap` to the file system 
        and returns the file path. It uses I/O operations, runs in the `Dispatchers.IO` context to avoid blocking the main thread, 
        and uses a `runOnUiThread` block to update the UI if necessary.
        */
        
        var result = ""
        withContext(Dispatchers.IO) {
            if (mBitmap != null) {

                try {
                    val bytes = ByteArrayOutputStream() // Creates a new byte array output stream.
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

                    val f = File()

                    val fo = FileOutputStream(f) // Creates a file output stream to write to the file represented by the specified object.
                    
                    fo.write(bytes.toByteArray()) // Writes bytes from the specified byte array to this file output stream.
                    fo.close() // Closes this file output stream and releases any system resources associated with this stream. This file output stream may no longer be used for writing bytes.
                    result = f.absolutePath // The file absolute path is return as a result.
                    //We switch from io to ui thread to show a toast
                    runOnUiThread {
                        
                    }
                } catch (e: Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }
        return result
    }


    // Method is used to show the Custom Progress Dialog.
    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog?.show()
    }


    // This function is used to dismiss the progress dialog if it is visible to user.
    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }

}
