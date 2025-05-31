package com.shrimpdevs.digitalassistant.screens.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shrimpdevs.digitalassistant.R
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import com.shrimpdevs.digitalassistant.ui.theme.BLueLight
import com.shrimpdevs.digitalassistant.ui.theme.Black
import com.shrimpdevs.digitalassistant.ui.theme.BlueDark
import com.shrimpdevs.digitalassistant.ui.theme.DarkBlue
import com.shrimpdevs.digitalassistant.ui.theme.DarkText
import com.shrimpdevs.digitalassistant.ui.theme.Gray
import com.shrimpdevs.digitalassistant.ui.theme.LightBlue
import kotlin.math.round

@Preview
@Composable
fun InitialScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DarkBlue, Black))),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Spacer(modifier = Modifier.weight(1f))
        //Logo de la aplicacion y el nombre
        Image(
            painter = painterResource(id =R.drawable.logo_da),
            contentDescription = "Logo de Aplicacion DA"
        )
        Text(
            text = "Digital Assistant",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        //Botones de inicio de sesion y registro
        Button(onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 5.dp)
                .shadow(10.dp),
            //Color de fondo del boton
            colors = ButtonDefaults.buttonColors(containerColor = DarkText),
            // Establecer radio de borde
            shape = RoundedCornerShape(15.dp),
            contentPadding = PaddingValues(
                top = 15.dp,
                bottom = 15.dp,
            )
        ) {
            Text(
                text = "Iniciar Sesión", color = Color.White,
            )
        }
        //Spacer(modifier = Modifier.height(50.dp)) //Este espacio es experiemntal para la separacion de los botones
        Button(onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp, vertical = 5.dp)
                .shadow(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BlueDark), //Color de fondo del boton
            border = BorderStroke(1.dp, DarkBlue),//Color de borde
            shape = RoundedCornerShape(15.dp),  // Establecer radio de borde
            contentPadding = PaddingValues(
                top = 15.dp,
                bottom = 15.dp,
            )
        ) {
            Text(
                text = "Crear nueva cuenta", color = Color.White,
            )
        }

        // Línea divisoria
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp)
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 100.dp)
                .border(
                    width = 1.dp,
                    color = Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .background(Color.White,shape = RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Botón de GOogle
            Button(
                onClick = { /*LOGIN con google*/ },
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp, 12.dp, 6.dp, 12.dp)
                    .shadow(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_icon_logo_svgrepo_com),
                    contentDescription = "Logo de Google",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            // Botón de Facebook
            Button(
                onClick = { /* LOGIN facebok */ },
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp, 12.dp, 12.dp, 12.dp)
                    .shadow(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(15.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook_1_svgrepo_com),
                    contentDescription = "Logo de Facebook",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

