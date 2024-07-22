package dev.pablorjd.listadeudores.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.pablorjd.listadeudores.data.DefaultingCustomerDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): DefaultingCustomerDB {
        return Room.databaseBuilder(context = context, klass = DefaultingCustomerDB::class.java, name = "defaulting_customers.db").build()
    }

    @Provides
    @Singleton
    fun DefaultingCustomerDao(db: DefaultingCustomerDB) = db.defaultingCustomerDao()


}