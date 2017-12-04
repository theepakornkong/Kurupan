package com.egco428.a13353.kurupan

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.widget.ImageView
import com.google.firebase.storage.*
import java.time.LocalDateTime
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.time.ZoneId
import java.time.ZonedDateTime


class DataActivity : AppCompatActivity() {

    var check: Boolean = false
    var noSearch : String = "121"
    lateinit var msgList : MutableList<dataClass>


    val mDatabase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    val myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("kurupans")

    val storageRef: StorageReference? = FirebaseStorage.getInstance().getReference("images")
    var imageRef = storageRef!!.child("baka.jpg")

    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var photoImageView1: ImageView
    lateinit var photoImageView2: ImageView
    lateinit var photoImageView3: ImageView
    lateinit var photoImageView4: ImageView
    var imageSelect : Int = 0




    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras
        check = data.getBoolean("logincheck")


//        val current = LocalDateTime.now()
//
//        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
//        val formatted = current.format(formatter)
//
//        Log.d("TEST",formatted )




        // read data from firebase ------------------------------------------------------------------------------------------------------------



//      myRef.orderByChild("no").equalTo("121").addValueEventListener(object : ValueEventListener{
//        override fun onDataChange(snapshot: DataSnapshot?) {
//            var notNull = false
//            for (child: DataSnapshot in snapshot!!.children) {
//                val dumpData: dataClass = child.getValue(dataClass::class.java)!!
//
//                Log.i("Firebase:", dumpData.name)
//
//                noeditText.setText(dumpData.no)
//                nameeditText.setText(dumpData.name)
//                typeeditText.setText(dumpData.type)
//                locationeditText.setText(dumpData.location)
//
//
//                notNull = true
//            }
//
//            if (notNull == false){
//                Log.i("Firebase:", "data not found")
//            }
//
//            Log.i("Firebase:", "finish read data")
//
//        }
        msgList = mutableListOf()

        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot?) {

                var notNull = false
                msgList.clear()

                for (child: DataSnapshot in snapshot!!.children) {
                    val dumpData: dataClass = child.getValue(dataClass::class.java)!!



                    if(dumpData.no == noSearch ){

                        msgList.add(dumpData!!)

                        Log.i("Firebase:", "Found-------------" )
                        Log.i("Firebase:", "no :" + dumpData.no)
                        Log.i("Firebase:", "name :" + dumpData.name)
                        Log.i("Firebase:", "type :" + dumpData.type)
                        Log.i("Firebase:", "location :" + dumpData.location)

                    }

                    notNull = true
                }

                if (notNull == false){
                    Log.i("Firebase:", "data not found")
                }else{
                    if (msgList.isNotEmpty()){
                        msgList.sortByDescending { it.dateTime }
                        noeditText.setText(msgList[0].no)
                        nameeditText.setText(msgList[0].name)
                        typeeditText.setText(msgList[0].type)
                        locationeditText.setText(msgList[0].location)
                        timeeditText.setText(msgList[0].dateTimeShow)
                    }

                }

                Log.i("Firebase:", "finish read data")



            }

            override fun onCancelled(e: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


        // download picture from firebaseStorage ------------------------------------------------------------------------------------------------------------

        imageRef = storageRef!!.child(noeditText.text.toString()+"/1.jpg")
        imageRef.getBytes(java.lang.Long.MAX_VALUE).addOnSuccessListener { bytes -> bytes

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView1.setImageBitmap(bitmap)
            Log.d("TEST","IMAGE1")

        }.addOnFailureListener { exception -> exception
//            mTextView.setText(String.format("Failure: %s", exception.message))
            Log.d("TEST","Fail")
        }

        imageRef = storageRef!!.child(noeditText.text.toString()+"/2.jpg")
        imageRef.getBytes(java.lang.Long.MAX_VALUE).addOnSuccessListener { bytes -> bytes

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView2.setImageBitmap(bitmap)
            Log.d("TEST","IMAGE2")

        }.addOnFailureListener { exception -> exception
//            mTextView.setText(String.format("Failure: %s", exception.message))
            Log.d("TEST","Fail")
        }

        imageRef = storageRef!!.child(noeditText.text.toString()+"/3.jpg")
        imageRef.getBytes(java.lang.Long.MAX_VALUE).addOnSuccessListener { bytes -> bytes

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView3.setImageBitmap(bitmap)
            Log.d("TEST","IMAGE3")

        }.addOnFailureListener { exception -> exception
//            mTextView.setText(String.format("Failure: %s", exception.message))
            Log.d("TEST","Fail")
        }
        imageRef = storageRef!!.child(noeditText.text.toString()+"/4.jpg")
        imageRef.getBytes(java.lang.Long.MAX_VALUE).addOnSuccessListener { bytes -> bytes

            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageView4.setImageBitmap(bitmap)
            Log.d("TEST","IMAGE4")

        }.addOnFailureListener { exception -> exception
//            mTextView.setText(String.format("Failure: %s", exception.message))
            Log.d("TEST","Fail")
        }

        timeeditText.isEnabled = false
        // disable editText to edit value & hidden button ------------------------------------------------------------------------------------------------------------
        if (check == false){

            noeditText.isEnabled = false
            nameeditText.isEnabled = false
            typeeditText.isEnabled = false
            locationeditText.isEnabled = false
            timeeditText.isEnabled = false

//            button.visibility = View.INVISIBLE
//            button.isEnabled = false

        }

        //PhotoImageView Setup----------------------------------------------------------------------------------------------------------

        photoImageView1 = findViewById(R.id.imageView1)
        photoImageView2 = findViewById(R.id.imageView2)
        photoImageView3 = findViewById(R.id.imageView3)
        photoImageView4 = findViewById(R.id.imageView4)

        // 4 picture button command ------------------------------------------------------------------------------------------------------------
        imageView1.setOnClickListener {
            pictureChange(1)
            //writeNewObj("123","aaa","tyy","loc")
        }
        imageView2.setOnClickListener {
            pictureChange(2)
            //writeNewObj("122","asd5","tyy","loc")
        }
        imageView3.setOnClickListener {
            pictureChange(3)
            //writeNewObj("121","mgSkT","tyy","loc")
        }
        imageView4.setOnClickListener {
            pictureChange(4)
        }

        // save to firebase button ---------------------------------------------------------------------------------------------------------------
        button.setOnClickListener {

            writeNewObj(noeditText.text.toString(),nameeditText.text.toString(),typeeditText.text.toString(),locationeditText.text.toString())
            uploadimage()


        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pagemenu,menu)
        return true
    }

    //Function picture change and setup ---------------------------------------------------------------------------------------------------------------------------------------------
    private fun pictureChange(imageNum : Int){
        if (check == true){
            Log.d("hello","login")
            Toast.makeText(this, "Login Session", Toast.LENGTH_SHORT).show()

            imageSelect = imageNum

            launchCamera()
        }
        else{
            Log.d("hello","not login")
            Toast.makeText(this, "Guest Session", Toast.LENGTH_SHORT).show()
        }
    }

    //dont forget to set policy for read/write database on firebase before check another bug firebase -----------------------------------------------------------------------------------------
    @RequiresApi(Build.VERSION_CODES.O)
    private fun writeNewObj(numberObj: String, nameObj: String, typeObj: String, locationObj: String) {

//        Log.d("TEST","SEC : " + ZonedDateTime.now(ZoneId.of("VST")).toEpochSecond())
//        Log.d("TEST","CURRENT : " + ZonedDateTime.now(ZoneId.of("VST")))

        val user = dataClass(numberObj, nameObj,typeObj,locationObj,ZonedDateTime.now(ZoneId.of("VST")).toEpochSecond().toString(),ZonedDateTime.now(ZoneId.of("VST")).toString())

        Log.d("TEST",noeditText.text.toString())

        //mDatabase.child("kurupans").child(numberObj).setValue(user)
        mDatabase.child("kurupans").push().setValue(user)
    }

    //open camera ----------------------------------------------------------------------------------------------------------------------------------------------------
    fun launchCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extra = data!!.extras
            val photo = extra.get("data") as Bitmap




            when (imageSelect) {
                1 -> photoImageView1.setImageBitmap(photo)
                2 -> photoImageView2.setImageBitmap(photo)
                3 -> photoImageView3.setImageBitmap(photo)
                4 -> photoImageView4.setImageBitmap(photo)
                else -> Log.d("TEST","ERROR CASE OF CHANGE IMAGE BUTTON")
            }

        }


    }

     fun uploadimage(){

         uploadPerQ(photoImageView1,1)
         uploadPerQ(photoImageView2,2)
         uploadPerQ(photoImageView3,3)
         uploadPerQ(photoImageView4,4)

    }

    private fun uploadPerQ(photoImageView : ImageView,fileName : Int){

        imageRef = storageRef!!.child(noeditText.text.toString()+"/"+fileName.toString()+".jpg")

        photoImageView.setDrawingCacheEnabled(true)
        photoImageView.buildDrawingCache()
        var bitmap : Bitmap = photoImageView.getDrawingCache()
        var baos : ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        var data : ByteArray = baos.toByteArray()
        imageRef!!.putBytes(data)
                        .addOnSuccessListener {
                    Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Upload Fail",Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {
                    Toast.makeText(this,"Uploading",Toast.LENGTH_SHORT).show()
                }

    }


}

// data class as dataStructure for firebase read/write ------------------------------------------------------------------------------------------------------------
class dataClass {

    var no: String = ""
    var name: String = ""
    var type: String = ""
    var location: String = ""

    var dateTime: String = ""
    var dateTimeShow: String = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(numberObj: String, nameObj: String, typeObj: String, locationObj: String, dateTimeObj: String, dateTimeShow: String) {
        this.no = numberObj
        this.name = nameObj
        this.type = typeObj
        this.location = locationObj

        this.dateTime = dateTimeObj
        this.dateTimeShow = dateTimeShow

    }

}
