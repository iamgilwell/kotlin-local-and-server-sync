package com.rivabu.localandserversync.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rivabu.localandserversync.Contact
import com.rivabu.localandserversync.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {
    val allContacts: LiveData<List<Contact>> = repository.allContacts.asLiveData()
    fun addContact(image: String, name: String, phone: String, email: String, address: String, notes: String) {
        viewModelScope.launch {
            val contact = Contact(
                id = 0,
                image = image,
                name = name,
                phone = phone,
                email = email,
                address = address,
                notes = notes
            )
            repository.insert(contact)
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}


class ContactViewModelFactory(private val repository: ContactRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(ContactViewModel::class.java)){
           return ContactViewModel(repository) as T
       }else{
           throw IllegalArgumentException("Unknown ViewModel class")
       }
    }
}
