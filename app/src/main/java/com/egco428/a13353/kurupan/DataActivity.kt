package com.egco428.a13353.kurupan

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_data.*
import com.google.firebase.storage.StorageReference
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import java.io.File
import android.support.v7.widget.ThemedSpinnerAdapter.Helper
import android.graphics.BitmapFactory
import android.graphics.Bitmap




class DataActivity : AppCompatActivity() {

    var check: Boolean = false


    val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("kurupans")





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras
        check = data.getBoolean("logincheck")


        // read data from firebase ------------------------------------------------------------------------------------------------------------
        myRef.orderByChild("no").equalTo("121").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot?) {
                var notNull = false
                for (child: DataSnapshot in snapshot!!.children) {
                    val dumpData: dataClass = child.getValue(dataClass::class.java)!!

                    Log.i("Firebase:", dumpData.name)

                    noeditText.setText(dumpData.no)
                    nameeditText.setText(dumpData.name)
                    typeeditText.setText(dumpData.type)
                    locationeditText.setText(dumpData.location)



                    notNull = true
                }

                if (notNull == false){
                    Log.i("Firebase:", "data not found")
                }

                Log.i("Firebase:", "finish read data")

            }

            override fun onCancelled(e: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        // download picture from firebaseStorage ------------------------------------------------------------------------------------------------------------

        val storageRef: StorageReference? = FirebaseStorage.getInstance().getReference()
        var file = Uri.fromFile(File("path/to/images/rivers.jpg"))
        var imageRef = storageRef!!.child("images/baka.jpg")

        imageRef.getBytes(java.lang.Long.MAX_VALUE).addOnSuccessListener { bytes -> bytes

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView1.setImageBitmap(bitmap)
            imageView2.setImageBitmap(bitmap)
            imageView3.setImageBitmap(bitmap)
            imageView4.setImageBitmap(bitmap)
            Log.d("TEST","IMAGE")

        }.addOnFailureListener { exception -> exception

//            mTextView.setText(String.format("Failure: %s", exception.message))
            Log.d("TEST","Fail")
        }


        // disable editText to edit value & hidden button ------------------------------------------------------------------------------------------------------------
        if (check == false){

            noeditText.isEnabled = false
            nameeditText.isEnabled = false
            typeeditText.isEnabled = false
            locationeditText.isEnabled = false

            button.visibility = View.INVISIBLE
            button.isEnabled = false

        }

        // 4 picture button command ------------------------------------------------------------------------------------------------------------
        imageView1.setOnClickListener {
            pictureChange()
            writeNewObj("123","aaa","tyy","loc","111")
        }
        imageView2.setOnClickListener {
            pictureChange()
        }
        imageView3.setOnClickListener {
            pictureChange()
        }
        imageView4.setOnClickListener {
            pictureChange()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pagemenu,menu)
        return true
    }

    private fun pictureChange(){
        if (check == true){
            Log.d("hello","login")
            Toast.makeText(this, "Login Session", Toast.LENGTH_SHORT).show()
        }
                else{
                    Log.d("hello","not login")
                    Toast.makeText(this, "Guest Session", Toast.LENGTH_SHORT).show()
                }
    }

    //dont forget to set policy for read/write database on firebase before check another bug firebase -----------------------------------------------------------------------------------------
    private fun writeNewObj(numberObj: String, nameObj: String, typeObj: String, locationObj: String, dateTimeObj: String) {
//        val user = dataClass(noeditText.text.toString(), nameeditText.text.toString(),typeeditText.text.toString().toString(),locationeditText.text.toString(),"30/11/2560")
        var num = "121"
        val user = dataClass(num,"asd","tyy","loc","111")

        Log.d("TEST",noeditText.toString())

        mDatabase.child("kurupans").child(num).setValue(user)
    }


}

// data class as dataStructure for firebase read/write ------------------------------------------------------------------------------------------------------------
class dataClass {

    var no: String = ""
    var name: String = ""
    var type: String = ""
    var location: String = ""

    var dateTime: String = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(numberObj: String, nameObj: String, typeObj: String, locationObj: String, dateTimeObj: String) {
        this.no = numberObj
        this.name = nameObj
        this.type = typeObj
        this.location = locationObj

        this.dateTime = dateTimeObj

    }

}
