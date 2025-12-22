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
import com.healthtracker.app.data.local.entities.VitalRecord;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalStateException;
import java.lang.Integer;
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
public final class VitalRecordDao_Impl implements VitalRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VitalRecord> __insertionAdapterOfVitalRecord;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<VitalRecord> __deletionAdapterOfVitalRecord;

  private final EntityDeletionOrUpdateAdapter<VitalRecord> __updateAdapterOfVitalRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public VitalRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVitalRecord = new EntityInsertionAdapter<VitalRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vital_records` (`id`,`userId`,`heartRate`,`spO2`,`systolicBp`,`diastolicBp`,`respiratoryRate`,`temperatureCelsius`,`bloodGlucose`,`source`,`notes`,`timestamp`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VitalRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        if (entity.getHeartRate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getHeartRate());
        }
        if (entity.getSpO2() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getSpO2());
        }
        if (entity.getSystolicBp() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSystolicBp());
        }
        if (entity.getDiastolicBp() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDiastolicBp());
        }
        if (entity.getRespiratoryRate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getRespiratoryRate());
        }
        if (entity.getTemperatureCelsius() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getTemperatureCelsius());
        }
        if (entity.getBloodGlucose() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getBloodGlucose());
        }
        statement.bindString(10, entity.getSource());
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp);
        }
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
      }
    };
    this.__deletionAdapterOfVitalRecord = new EntityDeletionOrUpdateAdapter<VitalRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vital_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VitalRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfVitalRecord = new EntityDeletionOrUpdateAdapter<VitalRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `vital_records` SET `id` = ?,`userId` = ?,`heartRate` = ?,`spO2` = ?,`systolicBp` = ?,`diastolicBp` = ?,`respiratoryRate` = ?,`temperatureCelsius` = ?,`bloodGlucose` = ?,`source` = ?,`notes` = ?,`timestamp` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VitalRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        if (entity.getHeartRate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getHeartRate());
        }
        if (entity.getSpO2() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getSpO2());
        }
        if (entity.getSystolicBp() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getSystolicBp());
        }
        if (entity.getDiastolicBp() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDiastolicBp());
        }
        if (entity.getRespiratoryRate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getRespiratoryRate());
        }
        if (entity.getTemperatureCelsius() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getTemperatureCelsius());
        }
        if (entity.getBloodGlucose() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getBloodGlucose());
        }
        statement.bindString(10, entity.getSource());
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp);
        }
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllByUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM vital_records WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE vital_records SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VitalRecord vitalRecord,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfVitalRecord.insertAndReturnId(vitalRecord);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<VitalRecord> vitalRecords,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVitalRecord.insert(vitalRecords);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final VitalRecord vitalRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVitalRecord.handle(vitalRecord);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VitalRecord vitalRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVitalRecord.handle(vitalRecord);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
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
  public Object getById(final long id, final Continuation<? super VitalRecord> $completion) {
    final String _sql = "SELECT * FROM vital_records WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VitalRecord>() {
      @Override
      @Nullable
      public VitalRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final VitalRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _result = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
  public Flow<List<VitalRecord>> getAllByUserFlow(final String userId) {
    final String _sql = "SELECT * FROM vital_records WHERE userId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vital_records"}, new Callable<List<VitalRecord>>() {
      @Override
      @NonNull
      public List<VitalRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<VitalRecord> _result = new ArrayList<VitalRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
  public Flow<VitalRecord> getLatestFlow(final String userId) {
    final String _sql = "SELECT * FROM vital_records WHERE userId = ? ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vital_records"}, new Callable<VitalRecord>() {
      @Override
      @Nullable
      public VitalRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final VitalRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _result = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
          } else {
            _result = null;
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
  public Object getLatest(final String userId,
      final Continuation<? super VitalRecord> $completion) {
    final String _sql = "SELECT * FROM vital_records WHERE userId = ? ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VitalRecord>() {
      @Override
      @Nullable
      public VitalRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final VitalRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _result = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
  public Flow<List<VitalRecord>> getByDateRangeFlow(final String userId, final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM vital_records WHERE userId = ? AND timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vital_records"}, new Callable<List<VitalRecord>>() {
      @Override
      @NonNull
      public List<VitalRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<VitalRecord> _result = new ArrayList<VitalRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
  public Flow<List<VitalRecord>> getRecentRecordsFlow(final String userId, final int limit) {
    final String _sql = "SELECT * FROM vital_records WHERE userId = ? ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vital_records"}, new Callable<List<VitalRecord>>() {
      @Override
      @NonNull
      public List<VitalRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<VitalRecord> _result = new ArrayList<VitalRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
  public Object getAverageHeartRate(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(heartRate) FROM vital_records WHERE userId = ? AND heartRate IS NOT NULL AND timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getFloat(0);
            }
            _result = _tmp_2;
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
  public Object getAverageSpO2(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(spO2) FROM vital_records WHERE userId = ? AND spO2 IS NOT NULL AND timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getFloat(0);
            }
            _result = _tmp_2;
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
  public Object getUnsyncedRecords(final Continuation<? super List<VitalRecord>> $completion) {
    final String _sql = "SELECT * FROM vital_records WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VitalRecord>>() {
      @Override
      @NonNull
      public List<VitalRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "heartRate");
          final int _cursorIndexOfSpO2 = CursorUtil.getColumnIndexOrThrow(_cursor, "spO2");
          final int _cursorIndexOfSystolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "systolicBp");
          final int _cursorIndexOfDiastolicBp = CursorUtil.getColumnIndexOrThrow(_cursor, "diastolicBp");
          final int _cursorIndexOfRespiratoryRate = CursorUtil.getColumnIndexOrThrow(_cursor, "respiratoryRate");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfBloodGlucose = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodGlucose");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<VitalRecord> _result = new ArrayList<VitalRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VitalRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Integer _tmpHeartRate;
            if (_cursor.isNull(_cursorIndexOfHeartRate)) {
              _tmpHeartRate = null;
            } else {
              _tmpHeartRate = _cursor.getInt(_cursorIndexOfHeartRate);
            }
            final Integer _tmpSpO2;
            if (_cursor.isNull(_cursorIndexOfSpO2)) {
              _tmpSpO2 = null;
            } else {
              _tmpSpO2 = _cursor.getInt(_cursorIndexOfSpO2);
            }
            final Integer _tmpSystolicBp;
            if (_cursor.isNull(_cursorIndexOfSystolicBp)) {
              _tmpSystolicBp = null;
            } else {
              _tmpSystolicBp = _cursor.getInt(_cursorIndexOfSystolicBp);
            }
            final Integer _tmpDiastolicBp;
            if (_cursor.isNull(_cursorIndexOfDiastolicBp)) {
              _tmpDiastolicBp = null;
            } else {
              _tmpDiastolicBp = _cursor.getInt(_cursorIndexOfDiastolicBp);
            }
            final Integer _tmpRespiratoryRate;
            if (_cursor.isNull(_cursorIndexOfRespiratoryRate)) {
              _tmpRespiratoryRate = null;
            } else {
              _tmpRespiratoryRate = _cursor.getInt(_cursorIndexOfRespiratoryRate);
            }
            final Float _tmpTemperatureCelsius;
            if (_cursor.isNull(_cursorIndexOfTemperatureCelsius)) {
              _tmpTemperatureCelsius = null;
            } else {
              _tmpTemperatureCelsius = _cursor.getFloat(_cursorIndexOfTemperatureCelsius);
            }
            final Float _tmpBloodGlucose;
            if (_cursor.isNull(_cursorIndexOfBloodGlucose)) {
              _tmpBloodGlucose = null;
            } else {
              _tmpBloodGlucose = _cursor.getFloat(_cursorIndexOfBloodGlucose);
            }
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Date _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new VitalRecord(_tmpId,_tmpUserId,_tmpHeartRate,_tmpSpO2,_tmpSystolicBp,_tmpDiastolicBp,_tmpRespiratoryRate,_tmpTemperatureCelsius,_tmpBloodGlucose,_tmpSource,_tmpNotes,_tmpTimestamp,_tmpIsSynced);
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
