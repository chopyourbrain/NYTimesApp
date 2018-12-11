package com.example.mikhail.exercise2.Database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Observable;

@android.arch.persistence.room.Dao
public interface NewsDao {
    @Query("SELECT * FROM NewsEntity")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    NewsEntity getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

    @Query("DELETE FROM NewsEntity")
    void deleteAll();
}
