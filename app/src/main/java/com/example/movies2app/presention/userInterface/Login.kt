package com.example.movies2app.userInterface

import android.annotation.SuppressLint
import android.content.res.Resources
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.PopUpToBuilder
import androidx.navigation.fragment.findNavController
import com.example.movies2app.R
import com.example.movies2app.context.MoviesApplication
import com.example.movies2app.navigation.MenuNavigation
import com.google.firebase.auth.FirebaseAuth
import io.github.muddz.styleabletoast.StyleableToast


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun LoginScreen(navController: NavHostController,mAuth: FirebaseAuth){
    var email by remember {
        mutableStateOf("")

    }

    var password by remember {
        mutableStateOf("")
    }


    var showPassword by remember { mutableStateOf(value = false) }

    var loader by remember {mutableStateOf(false)}
    var error by remember {mutableStateOf(false)}



    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.ColorGunmetal))

    ) {
        //Login Image
        Image(
            painter = painterResource(id = R.drawable.login),
            modifier = Modifier.width(250.dp), contentDescription = "login"
        )


        Spacer(modifier = Modifier.height(10.dp))

        //Login Text
        Text(
            text = "Login",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.ColorPlatinum)
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Please Text
        Text(
            text = "please sign in to continue",
            style =   TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.ColorPlatinum)
            )
        )

        Spacer(modifier = Modifier.height(15.dp))
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

        Spacer(modifier = Modifier.height(15.dp))
        //Password Text
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

        Spacer(modifier = Modifier.height(40.dp))

        //Login Button
        Button(

            onClick = {

                if (email.isNotEmpty()&&password.isNotEmpty()&&password.length>=6){
                    loader = !loader
                    signIN(email = email, pass = password,mAuth=mAuth, navController = navController)


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
            Text(text = "Login",
                style = TextStyle(
                    color = colorResource(id = R.color.ColorGraniteGray),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                ))

        }
        Spacer(modifier = Modifier.height(15.dp))
        //forget password
        Text(
            text = "forget password?",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                color = colorResource(id = R.color.ColorAmericanPurple)
            )
        )

        Spacer(modifier = Modifier.height(35.dp))

        Row(){
            Text(
                text = "Don't have an account?",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = colorResource(id = R.color.ColorAmericanPurple)
                )
            )

            Spacer(modifier = Modifier.width(10.dp))
            //Register
            Text(
                text = "Sign Up",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    color = colorResource(id = R.color.ColorAmericanPurple),

                ),
                modifier = Modifier.clickable {
                    navController.navigate("Register")
                }
            )
        }

    }



}


fun signIN( email:String, pass:String, mAuth:FirebaseAuth,navController: NavHostController){

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {

                StyleableToast.makeText(MoviesApplication.getApplicationContext(),"sucess login",R.style.exampleToast).show()

                navController.navigate(MenuNavigation.Home.route){
                    popUpTo("Login") {
                        inclusive=true
                        saveState = true

                    }
                    launchSingleTop = true
                    restoreState = true

                }

            } else {
                StyleableToast.makeText(MoviesApplication.getApplicationContext(),it.exception!!.localizedMessage.toString(),R.style.exampleToast).show()

            }
        }

}
