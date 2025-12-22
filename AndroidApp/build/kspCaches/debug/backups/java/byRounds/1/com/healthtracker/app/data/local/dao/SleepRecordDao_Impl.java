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
import com.healthtracker.app.data.local.entities.SleepRecord;
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
public final class SleepRecordDao_Impl implements SleepRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SleepRecord> __insertionAdapterOfSleepRecord;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SleepRecord> __deletionAdapterOfSleepRecord;

  private final EntityDeletionOrUpdateAdapter<SleepRecord> __updateAdapterOfSleepRecord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public SleepRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSleepRecord = new EntityInsertionAdapter<SleepRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sleep_records` (`id`,`userId`,`sleepStart`,`sleepEnd`,`totalMinutes`,`deepSleepMinutes`,`lightSleepMinutes`,`remSleepMinutes`,`awakeMinutes`,`sleepScore`,`timesAwakened`,`source`,`notes`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SleepRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getSleepStart());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getSleepEnd());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindLong(5, entity.getTotalMinutes());
        statement.bindLong(6, entity.getDeepSleepMinutes());
        statement.bindLong(7, entity.getLightSleepMinutes());
        statement.bindLong(8, entity.getRemSleepMinutes());
        statement.bindLong(9, entity.getAwakeMinutes());
        if (entity.getSleepScore() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getSleepScore());
        }
        statement.bindLong(11, entity.getTimesAwakened());
        statement.bindString(12, entity.getSource());
        if (entity.getNotes() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getNotes());
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
      }
    };
    this.__deletionAdapterOfSleepRecord = new EntityDeletionOrUpdateAdapter<SleepRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `sleep_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SleepRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSleepRecord = new EntityDeletionOrUpdateAdapter<SleepRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `sleep_records` SET `id` = ?,`userId` = ?,`sleepStart` = ?,`sleepEnd` = ?,`totalMinutes` = ?,`deepSleepMinutes` = ?,`lightSleepMinutes` = ?,`remSleepMinutes` = ?,`awakeMinutes` = ?,`sleepScore` = ?,`timesAwakened` = ?,`source` = ?,`notes` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SleepRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        final Long _tmp = __converters.dateToTimestamp(entity.getSleepStart());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getSleepEnd());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindLong(5, entity.getTotalMinutes());
        statement.bindLong(6, entity.getDeepSleepMinutes());
        statement.bindLong(7, entity.getLightSleepMinutes());
        statement.bindLong(8, entity.getRemSleepMinutes());
        statement.bindLong(9, entity.getAwakeMinutes());
        if (entity.getSleepScore() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getSleepScore());
        }
        statement.bindLong(11, entity.getTimesAwakened());
        statement.bindString(12, entity.getSource());
        if (entity.getNotes() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getNotes());
        }
        final int _tmp_2 = entity.isSynced() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAllByUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sleep_records WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE sleep_records SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SleepRecord sleepRecord,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSleepRecord.insertAndReturnId(sleepRecord);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final SleepRecord sleepRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSleepRecord.handle(sleepRecord);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SleepRecord sleepRecord,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSleepRecord.handle(sleepRecord);
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
  public Object getById(final long id, final Continuation<? super SleepRecord> $completion) {
    final String _sql = "SELECT * FROM sleep_records WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SleepRecord>() {
      @Override
      @Nullable
      public SleepRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final SleepRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_1;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_3;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
  public Flow<List<SleepRecord>> getAllByUserFlow(final String userId) {
    final String _sql = "SELECT * FROM sleep_records WHERE userId = ? ORDER BY sleepStart DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sleep_records"}, new Callable<List<SleepRecord>>() {
      @Override
      @NonNull
      public List<SleepRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SleepRecord> _result = new ArrayList<SleepRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SleepRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_1;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_3;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
  public Flow<SleepRecord> getLatestFlow(final String userId) {
    final String _sql = "SELECT * FROM sleep_records WHERE userId = ? ORDER BY sleepStart DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sleep_records"}, new Callable<SleepRecord>() {
      @Override
      @Nullable
      public SleepRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final SleepRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_1;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_3;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
      final Continuation<? super SleepRecord> $completion) {
    final String _sql = "SELECT * FROM sleep_records WHERE userId = ? ORDER BY sleepStart DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SleepRecord>() {
      @Override
      @Nullable
      public SleepRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final SleepRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_1;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_3;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
  public Flow<List<SleepRecord>> getByDateRangeFlow(final String userId, final Date startDate,
      final Date endDate) {
    final String _sql = "SELECT * FROM sleep_records WHERE userId = ? AND sleepStart BETWEEN ? AND ? ORDER BY sleepStart DESC";
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"sleep_records"}, new Callable<List<SleepRecord>>() {
      @Override
      @NonNull
      public List<SleepRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SleepRecord> _result = new ArrayList<SleepRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SleepRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_3;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_5;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _item = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
      final Continuation<? super List<SleepRecord>> $completion) {
    final String _sql = "SELECT * FROM sleep_records WHERE userId = ? AND sleepStart BETWEEN ? AND ? ORDER BY sleepStart DESC";
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SleepRecord>>() {
      @Override
      @NonNull
      public List<SleepRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SleepRecord> _result = new ArrayList<SleepRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SleepRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_3;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_5;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _item = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
  public Object getAverageSleepDuration(final String userId, final Date startDate,
      final Date endDate, final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(totalMinutes) FROM sleep_records WHERE userId = ? AND sleepStart BETWEEN ? AND ?";
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
  public Object getAverageSleepScore(final String userId, final Date startDate, final Date endDate,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(sleepScore) FROM sleep_records WHERE userId = ? AND sleepScore IS NOT NULL AND sleepStart BETWEEN ? AND ?";
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
  public Object getUnsyncedRecords(final Continuation<? super List<SleepRecord>> $completion) {
    final String _sql = "SELECT * FROM sleep_records WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SleepRecord>>() {
      @Override
      @NonNull
      public List<SleepRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfSleepStart = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepStart");
          final int _cursorIndexOfSleepEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepEnd");
          final int _cursorIndexOfTotalMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMinutes");
          final int _cursorIndexOfDeepSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "deepSleepMinutes");
          final int _cursorIndexOfLightSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "lightSleepMinutes");
          final int _cursorIndexOfRemSleepMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "remSleepMinutes");
          final int _cursorIndexOfAwakeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "awakeMinutes");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfTimesAwakened = CursorUtil.getColumnIndexOrThrow(_cursor, "timesAwakened");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<SleepRecord> _result = new ArrayList<SleepRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SleepRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final Date _tmpSleepStart;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfSleepStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfSleepStart);
            }
            final Date _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepStart = _tmp_1;
            }
            final Date _tmpSleepEnd;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSleepEnd)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfSleepEnd);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpSleepEnd = _tmp_3;
            }
            final int _tmpTotalMinutes;
            _tmpTotalMinutes = _cursor.getInt(_cursorIndexOfTotalMinutes);
            final int _tmpDeepSleepMinutes;
            _tmpDeepSleepMinutes = _cursor.getInt(_cursorIndexOfDeepSleepMinutes);
            final int _tmpLightSleepMinutes;
            _tmpLightSleepMinutes = _cursor.getInt(_cursorIndexOfLightSleepMinutes);
            final int _tmpRemSleepMinutes;
            _tmpRemSleepMinutes = _cursor.getInt(_cursorIndexOfRemSleepMinutes);
            final int _tmpAwakeMinutes;
            _tmpAwakeMinutes = _cursor.getInt(_cursorIndexOfAwakeMinutes);
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final int _tmpTimesAwakened;
            _tmpTimesAwakened = _cursor.getInt(_cursorIndexOfTimesAwakened);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new SleepRecord(_tmpId,_tmpUserId,_tmpSleepStart,_tmpSleepEnd,_tmpTotalMinutes,_tmpDeepSleepMinutes,_tmpLightSleepMinutes,_tmpRemSleepMinutes,_tmpAwakeMinutes,_tmpSleepScore,_tmpTimesAwakened,_tmpSource,_tmpNotes,_tmpIsSynced);
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
