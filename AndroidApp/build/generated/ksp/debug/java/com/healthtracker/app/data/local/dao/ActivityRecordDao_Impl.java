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
import com.healthtracker.app.data.local.entities.ActivityRecord;
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
public final class ActivityRecordDao_Impl implements ActivityRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ActivityRecord> __insertionAdapterOfActivityRecord;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ActivityRecord> __deletionAdapterOfActivityRecord;

  private final EntityDeletionOrUpdateAdapter<ActivityRecord> __updateAdapterOfActivityRecord;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSteps;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public ActivityRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfActivityRecord = new EntityInsertionAdapter<ActivityRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `activity_records` (`id`,`userId`,`date`,`steps`,`stepsGoal`,`distanceMeters`,`caloriesBurned`,`caloriesGoal`,`activeMinutes`,`activeMinutesGoal`,`floorsClimbed`,`source`,`lastUpdated`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ActivityRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindLong(4, entity.getSteps());
        statement.bindLong(5, entity.getStepsGoal());
        statement.bindDouble(6, entity.getDistanceMeters());
        statement.bindLong(7, entity.getCaloriesBurned());
        statement.bindLong(8, entity.getCaloriesGoal());
        statement.bindLong(9, entity.getActiveMinutes());
        statement.bindLong(10, entity.getActiveMinutesGoal());
        statement.bindLong(11, entity.getFloorsClimbed());
        statement.bindString(12, entity.getSource());
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_1);
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
      }
    };
    this.__deletionAdapterOfActivityRecord = new EntityDeletionOrUpdateAdapter<ActivityRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `activity_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ActivityRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfActivityRecord = new EntityDeletionOrUpdateAdapter<ActivityRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `activity_records` SET `id` = ?,`userId` = ?,`date` = ?,`steps` = ?,`stepsGoal` = ?,`distanceMeters` = ?,`caloriesBurned` = ?,`caloriesGoal` = ?,`activeMinutes` = ?,`activeMinutesGoal` = ?,`floorsClimbed` = ?,`source` = ?,`lastUpdated` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ActivityRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindLong(4, entity.getSteps());
        statement.bindLong(5, entity.getStepsGoal());
        statement.bindDouble(6, entity.getDistanceMeters());
        statement.bindLong(7, entity.getCaloriesBurned());
        statement.bindLong(8, entity.getCaloriesGoal());
        statement.bindLong(9, entity.getActiveMinutes());
        statement.bindLong(10, entity.getActiveMinutesGoal());
        statement.bindLong(11, entity.getFloorsClimbed());
        statement.bindString(12, entity.getSource());
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getLastUpdated());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_1);
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateSteps = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE activity_records SET steps = ?, lastUpdated = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllByUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM activity_records WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE activity_records SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ActivityRecord activityRecord,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfActivityRecord.insertAndReturnId(activityRecord);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ActivityRecord activityRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfActivityRecord.handle(activityRecord);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ActivityRecord activityRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfActivityRecord.handle(activityRecord);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSteps(final long id, final int steps, final Date lastUpdated,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSteps.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, steps);
        _argIndex = 2;
        final Long _tmp = __converters.dateToTimestamp(lastUpdated);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
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
          __preparedStmtOfUpdateSteps.release(_stmt);
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
  public Object getById(final long id, final Continuation<? super ActivityRecord> $completion) {
    final String _sql = "SELECT * FROM activity_records WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ActivityRecord>() {
      @Override
      @Nullable
      public ActivityRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final ActivityRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Object getByDate(final String userId, final Date date,
      final Continuation<? super ActivityRecord> $completion) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? AND date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ActivityRecord>() {
      @Override
      @Nullable
      public ActivityRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final ActivityRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_2;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_4;
            }
            final boolean _tmpIsSynced;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_5 != 0;
            _result = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Flow<ActivityRecord> getByDateFlow(final String userId, final Date date) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? AND date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"activity_records"}, new Callable<ActivityRecord>() {
      @Override
      @Nullable
      public ActivityRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final ActivityRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_2 = __converters.fromTimestamp(_tmp_1);
            if (_tmp_2 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_2;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_4 = __converters.fromTimestamp(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_4;
            }
            final boolean _tmpIsSynced;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_5 != 0;
            _result = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Flow<ActivityRecord> getTodayFlow(final String userId) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? ORDER BY date DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"activity_records"}, new Callable<ActivityRecord>() {
      @Override
      @Nullable
      public ActivityRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final ActivityRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Flow<List<ActivityRecord>> getAllByUserFlow(final String userId) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"activity_records"}, new Callable<List<ActivityRecord>>() {
      @Override
      @NonNull
      public List<ActivityRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ActivityRecord> _result = new ArrayList<ActivityRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Flow<List<ActivityRecord>> getByDateRangeFlow(final String userId, final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"activity_records"}, new Callable<List<ActivityRecord>>() {
      @Override
      @NonNull
      public List<ActivityRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ActivityRecord> _result = new ArrayList<ActivityRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_3;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _item = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Object getByDateRange(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super List<ActivityRecord>> $completion) {
    final String _sql = "SELECT * FROM activity_records WHERE userId = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ActivityRecord>>() {
      @Override
      @NonNull
      public List<ActivityRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ActivityRecord> _result = new ArrayList<ActivityRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_3;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _item = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
  public Object getTotalSteps(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(steps) FROM activity_records WHERE userId = ? AND date BETWEEN ? AND ?";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getInt(0);
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
  public Object getTotalCaloriesBurned(final String userId, final Date startDate,
      final Date endDate, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(caloriesBurned) FROM activity_records WHERE userId = ? AND date BETWEEN ? AND ?";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_2;
            if (_cursor.isNull(0)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getInt(0);
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
  public Object getAverageSteps(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(steps) FROM activity_records WHERE userId = ? AND date BETWEEN ? AND ?";
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
  public Object getUnsyncedRecords(final Continuation<? super List<ActivityRecord>> $completion) {
    final String _sql = "SELECT * FROM activity_records WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ActivityRecord>>() {
      @Override
      @NonNull
      public List<ActivityRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfStepsGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "stepsGoal");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfCaloriesBurned = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesBurned");
          final int _cursorIndexOfCaloriesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "caloriesGoal");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfActiveMinutesGoal = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutesGoal");
          final int _cursorIndexOfFloorsClimbed = CursorUtil.getColumnIndexOrThrow(_cursor, "floorsClimbed");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<ActivityRecord> _result = new ArrayList<ActivityRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpDate = _tmp_1;
            }
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final int _tmpStepsGoal;
            _tmpStepsGoal = _cursor.getInt(_cursorIndexOfStepsGoal);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpCaloriesBurned;
            _tmpCaloriesBurned = _cursor.getInt(_cursorIndexOfCaloriesBurned);
            final int _tmpCaloriesGoal;
            _tmpCaloriesGoal = _cursor.getInt(_cursorIndexOfCaloriesGoal);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpActiveMinutesGoal;
            _tmpActiveMinutesGoal = _cursor.getInt(_cursorIndexOfActiveMinutesGoal);
            final int _tmpFloorsClimbed;
            _tmpFloorsClimbed = _cursor.getInt(_cursorIndexOfFloorsClimbed);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final Date _tmpLastUpdated;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfLastUpdated);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpLastUpdated = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new ActivityRecord(_tmpId,_tmpUserId,_tmpDate,_tmpSteps,_tmpStepsGoal,_tmpDistanceMeters,_tmpCaloriesBurned,_tmpCaloriesGoal,_tmpActiveMinutes,_tmpActiveMinutesGoal,_tmpFloorsClimbed,_tmpSource,_tmpLastUpdated,_tmpIsSynced);
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
