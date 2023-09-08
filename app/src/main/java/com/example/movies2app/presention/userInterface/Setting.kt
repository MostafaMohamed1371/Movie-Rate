package com.example.movies2app.userInterface

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies2app.R
import com.example.movies2app.component.CoilImage
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.login.DataLogin
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import io.github.muddz.styleabletoast.StyleableToast
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(reference: DatabaseReference,userId: String){

    var showPassword by remember { mutableStateOf(value = false) }

    var nameChange = remember {
        mutableStateOf("")

    }

    var emailChange = remember {
        mutableStateOf("")

    }

    var passChange = remember {
        mutableStateOf("")
    }



    reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            val name=snapshot.child("userName").value.toString()
            val email=snapshot.child("email").value.toString()
            val pass=snapshot.child("password").value.toString()

            nameChange.value=name
            emailChange.value=email
            passChange.value=pass

        }


        override fun onCancelled(error: DatabaseError) {
            StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Fail to get data", R.style.exampleToast).show()
        }

    })


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.ColorGraniteGray))

    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {

//            Image(
//                painter = painterResource(id =R.drawable.person ),
//                contentDescription = "person",
//                contentScale = ContentScale.Crop,            // crop the image if it's not a square
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)                       // clip to the circle shape
//                    .border(2.dp, Color.Gray, CircleShape),   // add a border (optional)
//
//            )
//            val file: File = File.createTempFile("image","jpeg")
            var data by remember {
                mutableStateOf("")

            }
//            imageStorage.getFile(file).addOnSuccessListener {
//                val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
//                data=bitmap.toString()
//            }.addOnFailureListener {
//                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Image failed to load",
//                    R.style.exampleToast).show()
//            }
            CoilImage(
                data = data,
                contentDescription = "Account photot",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop,
            )

            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .padding(start = 50.dp, top = 90.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
            )

        }


        //Name Text
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.ColorGraniteGray),
                unfocusedBorderColor = colorResource(id = R.color.ColorGraniteGray)),
            value = nameChange.value,
            onValueChange ={change->nameChange.value=change},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Name",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            placeholder = { Text(text = "Enter your name",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "personIcon") },
            shape= RoundedCornerShape(18.dp)

        )

        Spacer(modifier = Modifier.height(5.dp))
        //Handle Name
        if (nameChange.value.isEmpty()){

            Text(
                text = "name is empty",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 200.dp)
            )
        }


        Spacer(modifier = Modifier.height(20.dp))
        //Email Text
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.ColorGraniteGray),
                unfocusedBorderColor = colorResource(id = R.color.ColorGraniteGray)),
            value = emailChange.value,
            onValueChange ={change->emailChange.value=change},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "Email address",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            placeholder = { Text(text = "Enter your e-mail",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            shape= RoundedCornerShape(18.dp)

        )

        Spacer(modifier = Modifier.height(5.dp))
        //Handle Email
        if (emailChange.value.isEmpty()){

            Text(
                text = "email is empty",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 200.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        //Password Tetx
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.ColorGraniteGray),
                unfocusedBorderColor = colorResource(id = R.color.ColorGraniteGray))

            ,value = passChange.value ,
            onValueChange ={change->passChange.value=change},
            label = { Text(text = "Password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            placeholder = { Text(text = "Enter your password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )
            ) },
            shape= RoundedCornerShape(18.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
            if (showPassword) {

                VisualTransformation.None

            } else {

                PasswordVisualTransformation()

            },
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }

        )

        Spacer(modifier = Modifier.height(5.dp))
        //Handle Password
        if (passChange.value.isEmpty()){

            Text(
                text = "password is empty",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        }else if (passChange.value.length<6){
            Text(
                text = "password is less than 6",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        }
        //Update Button
        Button(
            onClick = {
           updateProfile(reference = reference, nameChange = nameChange.value, emailChange =  emailChange.value, passChange =  passChange.value)

            },
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.ColorAmericanPurple)),
            shape = RoundedCornerShape(25.dp),

            ){
            Text(text = "Update",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ))

        }



    }


}
private fun checkPassword(passChange:String):Boolean{
    if(passChange.isEmpty()){
        StyleableToast.makeText(MoviesApplication.getApplicationContext(),"password is empty",
           R.style.exampleToast).show()
        return false
    }
    if(passChange.length<6){
        StyleableToast.makeText(MoviesApplication.getApplicationContext(),"password is leaast than 6 character",
            R.style.exampleToast).show()
        return false
    }
    return true

}

private fun checkEmail(emailChange:String):Boolean{
    val email=emailChange
    if(email==""){
        StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Email is empty",
           R.style.exampleToast).show()
        return false
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        StyleableToast.makeText(MoviesApplication.getApplicationContext(), "Please provide valid email!", R.style.exampleToast).show()
        return false
    }
    return true

}
private fun checkUserName(nameChange:String):Boolean{
    val userName=nameChange
    if(userName==""){
        StyleableToast.makeText(MoviesApplication.getApplicationContext(),"userName is empty",
            R.style.exampleToast).show()
        return false
    }

    return true

}




private fun updateProfile(reference: DatabaseReference,nameChange:String,emailChange:String,passChange:String) {

        if (nameChange.isEmpty() || emailChange.isEmpty() || passChange.isEmpty()) {
            StyleableToast.makeText(MoviesApplication.getApplicationContext(), "one or many field is empty", R.style.exampleToast).show()
        } else {

            if (checkUserName(nameChange = nameChange)) {
                reference.child(FirebaseAuth.getInstance().currentUser!!.uid).child("userName")
                    .setValue(nameChange)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                "update userName",
                                R.style.exampleToast).show()
                        } else {
                            StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                "failed update userName",
                                R.style.exampleToast).show()
                        }
                    }
            }
            val user2 = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider.getCredential(emailChange, passChange)
            user2!!.reauthenticate(credential).addOnCompleteListener { task: Task<Void?>? ->
                val user1 = FirebaseAuth.getInstance().currentUser
                if (checkEmail(emailChange = emailChange)) {
                    user1!!.updateEmail(emailChange)
                        .addOnCompleteListener { task1: Task<Void?> ->
                            if (task1.isSuccessful) {
                                reference.child(user2.uid).child("email")
                                    .setValue(emailChange)
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                    "update email",
                                    R.style.exampleToast).show()
                            } else {
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                    "fail update email",
                                    R.style.exampleToast).show()
                            }
                        }
                }
                if (checkPassword(passChange = passChange)) {
                    user1!!.updatePassword(passChange)
                        .addOnCompleteListener { task1: Task<Void?> ->
                            if (task1.isSuccessful) {
                                reference.child(user2.uid).child("password")
                                    .setValue(passChange)
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                    "update password",
                                    R.style.exampleToast).show()
                            } else {
                                StyleableToast.makeText(MoviesApplication.getApplicationContext(),
                                    "fail update password",
                                   R.style.exampleToast).show()
                            }
                        }
                }
            }



        }




}

private fun getImageFromFirebase(imageStorage: StorageReference,  data:String) {
    val file: File = File.createTempFile("image","jpeg")
    imageStorage.getFile(file).addOnSuccessListener {
        val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
    }.addOnFailureListener {
        StyleableToast.makeText(MoviesApplication.getApplicationContext(),"Image failed to load",
            R.style.exampleToast).show()
    }
}


