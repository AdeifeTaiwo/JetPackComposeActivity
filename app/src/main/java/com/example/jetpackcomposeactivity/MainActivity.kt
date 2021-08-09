package com.example.jetpackcomposeactivity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.jetpackcomposeactivity.ui.theme.JetPackComposeActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    PreviewConversation()

                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {

    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.download),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        // Add horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))
        //We keep track if the message is expanded or not in this variable
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor: Color by animateColorAsState(
            if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )

        // We toggle the isExpanded variable when we click on this Column
        

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )

            //Add a vertical space between the author and message text
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {

                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    //if the message is expanded, we display all its contents
                    // otherwise we only display the first line
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Dark mode"
)
@Composable
fun PreviewMessageCard() {
    JetPackComposeActivityTheme {
        MessageCard(msg = Message("Colleague", "Hey, take a look at JetPack Compose, Its great"))
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)

        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    JetPackComposeActivityTheme {
        Conversation(SampleData.conversationSample)

    }
}



