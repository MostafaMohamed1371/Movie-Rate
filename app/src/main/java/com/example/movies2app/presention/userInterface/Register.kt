package com.example.movies2app.userInterface


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movies2app.R
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.login.DataLogin
import com.example.movies2app.navigation.MenuNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.github.muddz.styleabletoast.StyleableToast


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun RegisterScreen(navController: NavHostController,mAuth:FirebaseAuth) {


    var name by remember {
        mutableStateOf("")

    }

    var email by remember {
        mutableStateOf("")

    }

    var password by remember {
        mutableStateOf("")
    }

    var Confirmpassword by remember {
        mutableStateOf("")
    }


    var showPassword by remember { mutableStateOf(value = false) }

    var loader by remember {mutableStateOf(false)}
    var error by remember {mutableStateOf(false)}





    Column(

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.ColorGunmetal))

    ) {

        Spacer(modifier = Modifier.height(20.dp))
       //handle back to from register to login
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            modifier = Modifier
                .align(Alignment.Start)
                .width(40.dp)
                .height(30.dp)
                .clickable {
                    navController.popBackStack()
                }
            ,
            contentDescription = "back"
        )

        Spacer(modifier = Modifier.height(30.dp))

        //Register Text
        Text(
            text = "Create Account",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.ColorPlatinum)
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Please Text
        Text(
            text = "please sign up to continue",
            style =   TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.ColorPlatinum)
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        //Name Text
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.ColorGraniteGray),
                unfocusedBorderColor = colorResource(id = R.color.ColorGraniteGray)),
            value = name,
            onValueChange ={change->name=change},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Name",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            placeholder = { Text(text = "Enter your name",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "personIcon") },
            shape= RoundedCornerShape(18.dp)

        )

        Spacer(modifier = Modifier.height(5.dp))
        //Handle Name
        if (name.isEmpty()&&error==true){

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
            value = email,
            onValueChange ={change->email=change},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "Email address",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            placeholder = { Text(text = "Enter your e-mail",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            shape= RoundedCornerShape(18.dp)

        )

        Spacer(modifier = Modifier.height(5.dp))
        //Handle Email
        if (email.isEmpty()&&error==true){

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

            ,value = password ,
            onValueChange ={change->password=change},
            label = { Text(text = "Password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            placeholder = { Text(text = "Enter your password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
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
        if (password.isEmpty()&& error==true){

            Text(
                text = "password is empty",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        }else if (password.length<6&&error==true){
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

        Spacer(modifier = Modifier.height(20.dp))
        //ConfirmPassword Text
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.ColorGraniteGray),
                unfocusedBorderColor = colorResource(id = R.color.ColorGraniteGray))

            ,value = Confirmpassword ,
            onValueChange ={change->Confirmpassword=change},
            label = { Text(text = "Confirm Password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
            placeholder = { Text(text = "Enter your confirm password",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray)

                )) },
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
        //Handle ConfirmPassword
        if (Confirmpassword.isEmpty() && error==true){

            Text(
                text = "password is empty",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        }else if (Confirmpassword.length<6&&error==true){
            Text(
                text = "password is less than 6",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        } else if (Confirmpassword!=password&&error==true){
            Text(
                text = "password is not confirm",
                style =   TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Red
                ),
                modifier = Modifier.padding(end = 170.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        //Register Button
        Button(
            onClick = {

                if (email.isNotEmpty()&&password.isNotEmpty()&&Confirmpassword.isNotEmpty()&&Confirmpassword==password&&password.length>=6&&Confirmpassword.length>=6){
                    loader = !loader
                    signUp(userName = name, email =email , pass =password , mAuth=mAuth)

                    navController.navigate(MenuNavigation.Home.route){
                        popUpTo("Register") {
                            inclusive=true

                        }


                    }

                }else{
                    error = true

                }

            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.ColorAmericanPurple)),
            shape = RoundedCornerShape(25.dp),

            ){
            Text(text = "Register",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ))

        }






    }


    //CircularProgress to loading
    Box(
        contentAlignment = Alignment.Center
    ) {
        if(loader){
            CircularProgressIndicator()
        }

    }





}
fun signUp(userName:String, email:String, pass:String, mAuth:FirebaseAuth) {

    mAuth.createUserWithEmailAndPassword(email, pass)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                var Register = DataLogin(userName, email, pass)
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("Register")

                myRef.child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(Register)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            StyleableToast.makeText(
                                MoviesApplication.getApplicationContext(),
                                "user has been registered successful",
                                R.style.exampleToast
                            ).show()
                        } else {
                            StyleableToast.makeText(
                                MoviesApplication.getApplicationContext(),
                                "Failed to register! try again",
                                R.style.exampleToast
                            ).show()
                        }
                    }

            } else {

                StyleableToast.makeText(
                    MoviesApplication.getApplicationContext(),
                    it.exception!!.localizedMessage.toString(),
                    R.style.exampleToast
                ).show()
            }
        }
}


