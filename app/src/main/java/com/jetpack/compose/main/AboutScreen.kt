package com.jetpack.compose.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetpack.compose.R
import com.jetpack.compose.ui.theme.Typography


@Composable
fun AboutScreen(navigateBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 30.dp, end = 20.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(70.dp)
                    )
                }
                Text(
                    text = "About Developer",
                    style = Typography.h2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth()
                )
            }
        }
        item {
            Row (Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(
                        id = R.drawable.developer
                    ),
                    contentDescription = "developer_photo",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(250.dp)
                        .fillMaxHeight()
                )
                Column (Modifier.padding(start = 20.dp, end = 20.dp)){
                    Text(text = "Windiarta", style = Typography.h2)
                    Divider()
                    Text(text = "a181dsx3462@bangkit.academy", style = Typography.h5)
                    Divider()
                    Text(text = "https://github.com/Windiarta", style = Typography.h5)
                }
            }

        }
    }
}