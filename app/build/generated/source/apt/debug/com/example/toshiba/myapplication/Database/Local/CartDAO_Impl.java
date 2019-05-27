package com.example.toshiba.myapplication.Database.Local;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.RxRoom;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.example.toshiba.myapplication.Database.ModelDB.Cart;
import io.reactivex.Flowable;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public class CartDAO_Impl implements CartDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCart;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCart;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfCart;

  private final SharedSQLiteStatement __preparedStmtOfEmptyCart;

  public CartDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCart = new EntityInsertionAdapter<Cart>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Cart`(`id`,`shopName`,`name`,`image`,`Quantity`,`price`,`color`,`size`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cart value) {
        stmt.bindLong(1, value.id);
        if (value.shopName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.shopName);
        }
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        if (value.image == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.image);
        }
        stmt.bindLong(5, value.quantity);
        stmt.bindDouble(6, value.price);
        stmt.bindLong(7, value.color);
        stmt.bindLong(8, value.size);
      }
    };
    this.__deletionAdapterOfCart = new EntityDeletionOrUpdateAdapter<Cart>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Cart` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cart value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__updateAdapterOfCart = new EntityDeletionOrUpdateAdapter<Cart>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Cart` SET `id` = ?,`shopName` = ?,`name` = ?,`image` = ?,`Quantity` = ?,`price` = ?,`color` = ?,`size` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cart value) {
        stmt.bindLong(1, value.id);
        if (value.shopName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.shopName);
        }
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        if (value.image == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.image);
        }
        stmt.bindLong(5, value.quantity);
        stmt.bindDouble(6, value.price);
        stmt.bindLong(7, value.color);
        stmt.bindLong(8, value.size);
        stmt.bindLong(9, value.id);
      }
    };
    this.__preparedStmtOfEmptyCart = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Cart";
        return _query;
      }
    };
  }

  @Override
  public void insertToCart(Cart... carts) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCart.insert(carts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCartItem(Cart cart) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCart.handle(cart);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCart(Cart... carts) {
    __db.beginTransaction();
    try {
      __updateAdapterOfCart.handleMultiple(carts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void emptyCart() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfEmptyCart.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfEmptyCart.release(_stmt);
    }
  }

  @Override
  public Flowable<List<Cart>> getCartItems() {
    final String _sql = "SELECT * FROM Cart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createFlowable(__db, new String[]{"Cart"}, new Callable<List<Cart>>() {
      @Override
      public List<Cart> call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfShopName = _cursor.getColumnIndexOrThrow("shopName");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
          final int _cursorIndexOfQuantity = _cursor.getColumnIndexOrThrow("Quantity");
          final int _cursorIndexOfPrice = _cursor.getColumnIndexOrThrow("price");
          final int _cursorIndexOfColor = _cursor.getColumnIndexOrThrow("color");
          final int _cursorIndexOfSize = _cursor.getColumnIndexOrThrow("size");
          final List<Cart> _result = new ArrayList<Cart>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cart _item;
            _item = new Cart();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.shopName = _cursor.getString(_cursorIndexOfShopName);
            _item.name = _cursor.getString(_cursorIndexOfName);
            _item.image = _cursor.getString(_cursorIndexOfImage);
            _item.quantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item.price = _cursor.getDouble(_cursorIndexOfPrice);
            _item.color = _cursor.getInt(_cursorIndexOfColor);
            _item.size = _cursor.getInt(_cursorIndexOfSize);
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
  public Flowable<List<Cart>> getCartItemById(int cartItemId) {
    final String _sql = "SELECT * FROM Cart WHERE id =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cartItemId);
    return RxRoom.createFlowable(__db, new String[]{"Cart"}, new Callable<List<Cart>>() {
      @Override
      public List<Cart> call() throws Exception {
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfShopName = _cursor.getColumnIndexOrThrow("shopName");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfImage = _cursor.getColumnIndexOrThrow("image");
          final int _cursorIndexOfQuantity = _cursor.getColumnIndexOrThrow("Quantity");
          final int _cursorIndexOfPrice = _cursor.getColumnIndexOrThrow("price");
          final int _cursorIndexOfColor = _cursor.getColumnIndexOrThrow("color");
          final int _cursorIndexOfSize = _cursor.getColumnIndexOrThrow("size");
          final List<Cart> _result = new ArrayList<Cart>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cart _item;
            _item = new Cart();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.shopName = _cursor.getString(_cursorIndexOfShopName);
            _item.name = _cursor.getString(_cursorIndexOfName);
            _item.image = _cursor.getString(_cursorIndexOfImage);
            _item.quantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item.price = _cursor.getDouble(_cursorIndexOfPrice);
            _item.color = _cursor.getInt(_cursorIndexOfColor);
            _item.size = _cursor.getInt(_cursorIndexOfSize);
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
  public int countCartItems() {
    final String _sql = "SELECT COUNT(*) from Cart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public float sumPrice() {
    final String _sql = "SELECT SUM(Price) from Cart";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final float _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getFloat(0);
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
