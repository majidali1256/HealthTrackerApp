package com.healthtracker.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.healthtracker.app.data.local.Converters;
import com.healthtracker.app.data.local.entities.Medication;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MedicationDao_Impl implements MedicationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Medication> __insertionAdapterOfMedication;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Medication> __deletionAdapterOfMedication;

  private final EntityDeletionOrUpdateAdapter<Medication> __updateAdapterOfMedication;

  private final SharedSQLiteStatement __preparedStmtOfDecrementQuantity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateQuantity;

  private final SharedSQLiteStatement __preparedStmtOfSetActive;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public MedicationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedication = new EntityInsertionAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `medications` (`id`,`userId`,`name`,`dosage`,`dosageUnit`,`frequency`,`scheduleTimes`,`instructions`,`takeWithFood`,`startDate`,`endDate`,`currentQuantity`,`refillAt`,`refillDate`,`isActive`,`createdAt`,`updatedAt`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDosage());
        statement.bindString(5, entity.getDosageUnit());
        statement.bindString(6, entity.getFrequency());
        statement.bindString(7, entity.getScheduleTimes());
        if (entity.getInstructions() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getInstructions());
        }
        final int _tmp = entity.getTakeWithFood() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getStartDate());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getEndDate());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp_2);
        }
        statement.bindLong(12, entity.getCurrentQuantity());
        statement.bindLong(13, entity.getRefillAt());
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getRefillDate());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final int _tmp_4 = entity.isActive() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final Long _tmp_5 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_5);
        }
        final Long _tmp_6 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(17);
        } else {
          statement.bindLong(17, _tmp_6);
        }
        final int _tmp_7 = entity.isSynced() ? 1 : 0;
        statement.bindLong(18, _tmp_7);
      }
    };
    this.__deletionAdapterOfMedication = new EntityDeletionOrUpdateAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `medications` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMedication = new EntityDeletionOrUpdateAdapter<Medication>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medications` SET `id` = ?,`userId` = ?,`name` = ?,`dosage` = ?,`dosageUnit` = ?,`frequency` = ?,`scheduleTimes` = ?,`instructions` = ?,`takeWithFood` = ?,`startDate` = ?,`endDate` = ?,`currentQuantity` = ?,`refillAt` = ?,`refillDate` = ?,`isActive` = ?,`createdAt` = ?,`updatedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Medication entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDosage());
        statement.bindString(5, entity.getDosageUnit());
        statement.bindString(6, entity.getFrequency());
        statement.bindString(7, entity.getScheduleTimes());
        if (entity.getInstructions() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getInstructions());
        }
        final int _tmp = entity.getTakeWithFood() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getStartDate());
        if (_tmp_1 == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getEndDate());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, _tmp_2);
        }
        statement.bindLong(12, entity.getCurrentQuantity());
        statement.bindLong(13, entity.getRefillAt());
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getRefillDate());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final int _tmp_4 = entity.isActive() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final Long _tmp_5 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_5);
        }
        final Long _tmp_6 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(17);
        } else {
          statement.bindLong(17, _tmp_6);
        }
        final int _tmp_7 = entity.isSynced() ? 1 : 0;
        statement.bindLong(18, _tmp_7);
        statement.bindLong(19, entity.getId());
      }
    };
    this.__preparedStmtOfDecrementQuantity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE medications SET currentQuantity = currentQuantity - 1 WHERE id = ? AND currentQuantity > 0";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateQuantity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE medications SET currentQuantity = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetActive = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE medications SET isActive = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllByUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM medications WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE medications SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Medication medication, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMedication.insertAndReturnId(medication);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Medication medication, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Medication medication, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedication.handle(medication);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object decrementQuantity(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDecrementQuantity.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDecrementQuantity.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateQuantity(final long id, final int quantity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateQuantity.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, quantity);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateQuantity.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object setActive(final long id, final boolean isActive,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetActive.acquire();
        int _argIndex = 1;
        final int _tmp = isActive ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetActive.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllByUser(final String userId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllByUser.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, userId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllByUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final long id, final boolean isSynced,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSyncStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isSynced ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super Medication> $completion) {
    final String _sql = "SELECT * FROM medications WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Medication>() {
      @Override
      @Nullable
      public Medication call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final Medication _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _result = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Medication>> getAllByUserFlow(final String userId) {
    final String _sql = "SELECT * FROM medications WHERE userId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medications"}, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Flow<List<Medication>> getActiveMedicationsFlow(final String userId) {
    final String _sql = "SELECT * FROM medications WHERE userId = ? AND isActive = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medications"}, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getActiveMedications(final String userId,
      final Continuation<? super List<Medication>> $completion) {
    final String _sql = "SELECT * FROM medications WHERE userId = ? AND isActive = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Medication>> getMedicationsNeedingRefillFlow(final String userId) {
    final String _sql = "SELECT * FROM medications WHERE userId = ? AND currentQuantity <= refillAt AND isActive = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medications"}, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getUnsyncedMedications(final Continuation<? super List<Medication>> $completion) {
    final String _sql = "SELECT * FROM medications WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Medication>>() {
      @Override
      @NonNull
      public List<Medication> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDosage = CursorUtil.getColumnIndexOrThrow(_cursor, "dosage");
          final int _cursorIndexOfDosageUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "dosageUnit");
          final int _cursorIndexOfFrequency = CursorUtil.getColumnIndexOrThrow(_cursor, "frequency");
          final int _cursorIndexOfScheduleTimes = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduleTimes");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfTakeWithFood = CursorUtil.getColumnIndexOrThrow(_cursor, "takeWithFood");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "currentQuantity");
          final int _cursorIndexOfRefillAt = CursorUtil.getColumnIndexOrThrow(_cursor, "refillAt");
          final int _cursorIndexOfRefillDate = CursorUtil.getColumnIndexOrThrow(_cursor, "refillDate");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Medication> _result = new ArrayList<Medication>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Medication _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDosage;
            _tmpDosage = _cursor.getString(_cursorIndexOfDosage);
            final String _tmpDosageUnit;
            _tmpDosageUnit = _cursor.getString(_cursorIndexOfDosageUnit);
            final String _tmpFrequency;
            _tmpFrequency = _cursor.getString(_cursorIndexOfFrequency);
            final String _tmpScheduleTimes;
            _tmpScheduleTimes = _cursor.getString(_cursorIndexOfScheduleTimes);
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final boolean _tmpTakeWithFood;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfTakeWithFood);
            _tmpTakeWithFood = _tmp != 0;
            final Date _tmpStartDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfStartDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_2;
            }
            final Date _tmpEndDate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __converters.fromTimestamp(_tmp_3);
            final int _tmpCurrentQuantity;
            _tmpCurrentQuantity = _cursor.getInt(_cursorIndexOfCurrentQuantity);
            final int _tmpRefillAt;
            _tmpRefillAt = _cursor.getInt(_cursorIndexOfRefillAt);
            final Date _tmpRefillDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfRefillDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfRefillDate);
            }
            _tmpRefillDate = __converters.fromTimestamp(_tmp_4);
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final Date _tmpCreatedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_7 = __converters.fromTimestamp(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_9 = __converters.fromTimestamp(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsSynced;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_10 != 0;
            _item = new Medication(_tmpId,_tmpUserId,_tmpName,_tmpDosage,_tmpDosageUnit,_tmpFrequency,_tmpScheduleTimes,_tmpInstructions,_tmpTakeWithFood,_tmpStartDate,_tmpEndDate,_tmpCurrentQuantity,_tmpRefillAt,_tmpRefillDate,_tmpIsActive,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
