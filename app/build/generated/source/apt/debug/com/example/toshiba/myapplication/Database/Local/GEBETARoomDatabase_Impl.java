package com.example.toshiba.myapplication.Database.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class GEBETARoomDatabase_Impl extends GEBETARoomDatabase {
  private volatile CartDAO _cartDAO;

  private volatile FavoritesDAO _favoritesDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Cart` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `shopName` TEXT, `name` TEXT, `image` TEXT, `Quantity` INTEGER NOT NULL, `price` REAL NOT NULL, `color` INTEGER NOT NULL, `size` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Favorite` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `price` TEXT, `menuId` TEXT, `link` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"d03b7d1d8f4b2e5173eb6bf1709c6c0c\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Cart`");
        _db.execSQL("DROP TABLE IF EXISTS `Favorite`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCart = new HashMap<String, TableInfo.Column>(8);
        _columnsCart.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsCart.put("shopName", new TableInfo.Column("shopName", "TEXT", false, 0));
        _columnsCart.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsCart.put("image", new TableInfo.Column("image", "TEXT", false, 0));
        _columnsCart.put("Quantity", new TableInfo.Column("Quantity", "INTEGER", true, 0));
        _columnsCart.put("price", new TableInfo.Column("price", "REAL", true, 0));
        _columnsCart.put("color", new TableInfo.Column("color", "INTEGER", true, 0));
        _columnsCart.put("size", new TableInfo.Column("size", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCart = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCart = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCart = new TableInfo("Cart", _columnsCart, _foreignKeysCart, _indicesCart);
        final TableInfo _existingCart = TableInfo.read(_db, "Cart");
        if (! _infoCart.equals(_existingCart)) {
          throw new IllegalStateException("Migration didn't properly handle Cart(com.example.toshiba.myapplication.Database.ModelDB.Cart).\n"
                  + " Expected:\n" + _infoCart + "\n"
                  + " Found:\n" + _existingCart);
        }
        final HashMap<String, TableInfo.Column> _columnsFavorite = new HashMap<String, TableInfo.Column>(5);
        _columnsFavorite.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsFavorite.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsFavorite.put("price", new TableInfo.Column("price", "TEXT", false, 0));
        _columnsFavorite.put("menuId", new TableInfo.Column("menuId", "TEXT", false, 0));
        _columnsFavorite.put("link", new TableInfo.Column("link", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorite = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorite = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavorite = new TableInfo("Favorite", _columnsFavorite, _foreignKeysFavorite, _indicesFavorite);
        final TableInfo _existingFavorite = TableInfo.read(_db, "Favorite");
        if (! _infoFavorite.equals(_existingFavorite)) {
          throw new IllegalStateException("Migration didn't properly handle Favorite(com.example.toshiba.myapplication.Database.ModelDB.Favorites).\n"
                  + " Expected:\n" + _infoFavorite + "\n"
                  + " Found:\n" + _existingFavorite);
        }
      }
    }, "d03b7d1d8f4b2e5173eb6bf1709c6c0c", "f42ef14f41f2dc3c3d1342bf3b0e0c37");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Cart","Favorite");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Cart`");
      _db.execSQL("DELETE FROM `Favorite`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public CartDAO cartDAO() {
    if (_cartDAO != null) {
      return _cartDAO;
    } else {
      synchronized(this) {
        if(_cartDAO == null) {
          _cartDAO = new CartDAO_Impl(this);
        }
        return _cartDAO;
      }
    }
  }

  @Override
  public FavoritesDAO favoritesDAO() {
    if (_favoritesDAO != null) {
      return _favoritesDAO;
    } else {
      synchronized(this) {
        if(_favoritesDAO == null) {
          _favoritesDAO = new FavoritesDAO_Impl(this);
        }
        return _favoritesDAO;
      }
    }
  }
}
