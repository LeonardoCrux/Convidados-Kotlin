package com.pi.service.repository

import android.content.ContentValues
import android.content.Context
import com.pi.service.contants.DataBaseConstants.GUEST.COLUMNS.ID
import com.pi.service.contants.DataBaseConstants.GUEST.COLUMNS.NAME
import com.pi.service.contants.DataBaseConstants.GUEST.COLUMNS.PRESENCE
import com.pi.service.contants.DataBaseConstants.GUEST.TABLE_NAME
import com.pi.convidadoskotlin.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {

    private var mGuestDataBaseHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(NAME, guest.name)
            contentValues.put(PRESENCE, guest.presence)
            db.insert(TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }

    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(NAME, guest.name)
            contentValues.put(PRESENCE, guest.presence)
            val selection = ID + " = ?"
            val args = arrayOf(guest.id.toString())
            db.update(TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mGuestDataBaseHelper.writableDatabase
            val selection = ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try{
            val db = mGuestDataBaseHelper.readableDatabase
            val cursor = db.rawQuery("select * from Guest", null)
            if(cursor != null && cursor.count> 0){
                while (cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(PRESENCE)) == 1)
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }
            }
            return list
        }catch (e: Exception){
            return list
        }
    }

    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try{
            val db = mGuestDataBaseHelper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM Guest WHERE presence = 1", null)

                if(cursor != null && cursor.count> 0) {
                    while (cursor.moveToNext()) {
                        val id = cursor.getInt(cursor.getColumnIndex(ID))
                        val name = cursor.getString(cursor.getColumnIndex(NAME))
                        val presence = (cursor.getInt(cursor.getColumnIndex(PRESENCE)) == 1)
                        val guest = GuestModel(id, name, presence)
                        list.add(guest)
                }
            }
            cursor?.close()
            return list
        }catch(e:Exception){
            return list
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null
        return try {
            val db = mGuestDataBaseHelper.readableDatabase
            val projection = arrayOf(NAME, PRESENCE)

            val selection = ID + " = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query(TABLE_NAME, projection, selection, args, null, null, null)

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val name = cursor.getString(cursor.getColumnIndex(NAME))
                val presence = (cursor.getInt(cursor.getColumnIndex(PRESENCE)) == 1)
                guest = GuestModel(id, name, presence)

            }
            cursor.close()
            guest
        } catch (e: Exception) {
            guest
        }
    }

    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        return try {
            val db = mGuestDataBaseHelper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM Guest WHERE presence = 0", null)
            if(cursor != null && cursor.count>0){
                while(cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val name = cursor.getString(cursor.getColumnIndex(NAME))
                    val presence = (cursor.getInt(cursor.getColumnIndex(PRESENCE)) == 1)
                    val guest = GuestModel(id, name, presence)
                    list.add(guest)
                }

            }
            return list
        } catch (e:Exception){
            return list
        }
    }
}