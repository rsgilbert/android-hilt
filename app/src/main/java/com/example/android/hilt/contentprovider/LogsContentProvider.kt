package com.example.android.hilt.contentprovider

import android.content.*
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import com.example.android.hilt.di.DatabaseModule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.lang.UnsupportedOperationException


/**
 * A ContentProvider that exposes the logs outside the application process.
 */
class LogsContentProvider: ContentProvider() {
    private val LOGS_TABLE = "logs"
    // authority of content provider
    private val AUTHORITY = "com.example.android.hilt.provider"
    // match code for all items in the Logs table
    private val CODE_LOGS_DIR = 1
    // match code for one item in the logs table
    private val CODE_LOGS_ITEM = 2

    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, LOGS_TABLE, CODE_LOGS_ITEM)
        addURI(AUTHORITY, "$LOGS_TABLE/*", CODE_LOGS_ITEM)
    }

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface LogsContentProviderEntryPoint {
        fun logDao() : LogDao
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code : Int = matcher.match(uri)
        return if (code == CODE_LOGS_DIR || code == CODE_LOGS_ITEM) {
            val appContext = context?.applicationContext ?: throw IllegalStateException()
            val logDao : LogDao = getLogDao(context!!.applicationContext)
            val cursor : Cursor? = if (code == CODE_LOGS_DIR) {
                val id = ContentUris.parseId(uri)
                logDao.selectLogByIdCursor(id)
            }
            else {
                logDao.selectAllLogsCursor()
            }
            cursor?.setNotificationUri(appContext.contentResolver, uri)
            cursor
        }
        else {
            throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun getType(p0: Uri): String? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

//    fun getLogDao() : LogDao {
//        val appDatabase = Room.databaseBuilder(
//            context!!,
//            AppDatabase::class.java,
//            "logging.db"
//        ).build()
//        return appDatabase.logDao()
//    }

    private fun getLogDao(applicationContext: Context) : LogDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(applicationContext, LogsContentProviderEntryPoint::class.java)
        return hiltEntryPoint.logDao()
    }
}