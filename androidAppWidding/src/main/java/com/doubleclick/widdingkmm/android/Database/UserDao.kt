/*
package com.doubleclick.widdingkmm.android.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doubleclick.widdingkmm.android.Model.User
import retrofit2.http.Query

*/
/**
 * Created By Eslam Ghazy on 7/15/2022
 *//*


@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM User")
    fun  // to delete all data in table
            deleteAllData()

    @Query("SELECT * FROM User")
    fun getUserList(): List<User>

    @Query("SELECT * FROM User WHERE id==:id")
    @Transaction
    fun getUserById(id: String): LiveData<User>
}
*/
