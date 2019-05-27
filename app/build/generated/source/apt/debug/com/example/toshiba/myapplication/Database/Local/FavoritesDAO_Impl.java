package com.example.toshiba.myapplication.Database.Local;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.RxRoom;
import android.database.Cursor;
import com.example.toshiba.myapplication.Database.ModelDB.Favorites;
import io.reactivex.Flowable;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public class FavoritesDAO_Impl implements FavoritesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFavorites;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfFavorites;

  public FavoritesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorites = new EntityInsertionAdapter<Favorites>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Favorite`(`id`,`name`,`price`,`menuId`,`link`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favorites value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.price == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.price);
        }
        if (value.number == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.number);
        }
        if (value.link == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.link);
        }
      }
    };
    this.__deletionAdapterOfFavorites = new EntityDeletionOrUpdateAdapter<Favorites>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Favorite` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Favorites value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public void insertFav(Favorites... favorites) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavorites.insert(favorites);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Favorites favorites) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfFavorites.handle(favorites);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Flowable<List<Favorites>> getFavItems() {
    final String _sql = "SELECT * FROM Favorite";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createFlowable(__db, new String[]{"Favorite"}, new Callable<List<Favorites>>() {
      @Override
      public List<Favorites> call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfPrice = _cursor.getColumnIndexOrThrow("price");
          final int _cursorIndexOfNumber = _cursor.getColumnIndexOrThrow("menuId");
          final int _cursorIndexOfLink = _cursor.getColumnIndexOrThrow("link");
          final List<Favorites> _result = new ArrayList<Favorites>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Favorites _item;
            _item = new Favorites();
            _item.id = _cursor.getLong(_cursorIndexOfId);
            _item.name = _cursor.getString(_cursorIndexOfName);
            _item.price = _cursor.getString(_cursorIndexOfPrice);
            _item.number = _cursor.getString(_cursorIndexOfNumber);
            _item.link = _cursor.getString(_cursorIndexOfLink);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public long isFavorite(long itemId) {
    final String _sql = "SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, itemId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final long _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getLong(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
