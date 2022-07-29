/*
package com.doubleclick.widdingkmm.android.Database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doubleclick.widdingkmm.android.Model.ChatList
import com.doubleclick.widdingkmm.android.Model.MessageModel
import com.doubleclick.widdingkmm.android.Model.User

*/
/**
 * Created By Eslam Ghazy on 7/15/2022
 *//*

@Database(entities = [MessageModel::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {

    //    abstract fun EntitiesChatListDAO(): ChatListDao?
    abstract fun EntitiesUserDaoDAO(): UserDao?
    abstract fun EntitiesDAO(): ChatDao?

    companion object {
        private var instance: MessageDatabase? = null

        //Singleton
        @Synchronized
        fun getInstance(application: Application): MessageDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application.applicationContext,
                    MessageDatabase::class.java, "MessageDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}
*/
