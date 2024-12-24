package com.rivabu.localandserversync

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.room.Room
import com.rivabu.localandserversync.model.ContactViewModel
import com.rivabu.localandserversync.model.ContactViewModelFactory
import com.rivabu.localandserversync.repository.ContactRepository
import com.rivabu.localandserversync.ui.theme.LocalAndServerSyncTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(applicationContext, ContactDatabase::class.java, "contact_database").build()
        val repository = ContactRepository(database.contactDao())
        val viewModel: ContactViewModel by viewModels { ContactViewModelFactory(repository) }
        setContent {

        }
    }
}

@Composable
fun AddContactScreen(viewModel: ContactViewModel,navController: NavController){
    val context = LocalContext.current.applicationContext
    var imageUri by remember{
        mutableStateOf<Uri?>(value: null)
    }
}

fun copyUriToInternalStorage(context: Context, uri: Uri, fileName: String): String? {
    val file = File(context.filesDir, fileName)
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
