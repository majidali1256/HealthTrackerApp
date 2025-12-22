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
import com.healthtracker.app.data.local.entities.User;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`email`,`displayName`,`profilePictureUrl`,`dateOfBirth`,`gender`,`heightCm`,`weightKg`,`bloodType`,`medicalId`,`allergies`,`medicalConditions`,`organDonor`,`emergencyContacts`,`createdAt`,`updatedAt`,`isSynced`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getDisplayName());
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProfilePictureUrl());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getDateOfBirth());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        if (entity.getGender() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getGender());
        }
        if (entity.getHeightCm() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getHeightCm());
        }
        if (entity.getWeightKg() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getWeightKg());
        }
        if (entity.getBloodType() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getBloodType());
        }
        if (entity.getMedicalId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getMedicalId());
        }
        if (entity.getAllergies() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAllergies());
        }
        if (entity.getMedicalConditions() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getMedicalConditions());
        }
        final int _tmp_1 = entity.getOrganDonor() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        if (entity.getEmergencyContacts() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getEmergencyContacts());
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_2);
        }
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_3);
        }
        final int _tmp_4 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `users` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`email` = ?,`displayName` = ?,`profilePictureUrl` = ?,`dateOfBirth` = ?,`gender` = ?,`heightCm` = ?,`weightKg` = ?,`bloodType` = ?,`medicalId` = ?,`allergies` = ?,`medicalConditions` = ?,`organDonor` = ?,`emergencyContacts` = ?,`createdAt` = ?,`updatedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getDisplayName());
        if (entity.getProfilePictureUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProfilePictureUrl());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getDateOfBirth());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        if (entity.getGender() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getGender());
        }
        if (entity.getHeightCm() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getHeightCm());
        }
        if (entity.getWeightKg() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getWeightKg());
        }
        if (entity.getBloodType() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getBloodType());
        }
        if (entity.getMedicalId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getMedicalId());
        }
        if (entity.getAllergies() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAllergies());
        }
        if (entity.getMedicalConditions() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getMedicalConditions());
        }
        final int _tmp_1 = entity.getOrganDonor() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        if (entity.getEmergencyContacts() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getEmergencyContacts());
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_2);
        }
        final Long _tmp_3 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_3);
        }
        final int _tmp_4 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        statement.bindString(18, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
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
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSyncStatus(final String userId, final boolean isSynced,
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
          __preparedStmtOfUpdateSyncStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserById(final String userId, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _result = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Flow<User> getUserByIdFlow(final String userId) {
    final String _sql = "SELECT * FROM users WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _result = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getUserByEmail(final String email, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, email);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _result = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getCurrentUser(final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _result = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Flow<User> getCurrentUserFlow() {
    final String _sql = "SELECT * FROM users LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"users"}, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _result = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
  public Object getUnsyncedUsers(final Continuation<? super List<User>> $completion) {
    final String _sql = "SELECT * FROM users WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<User>>() {
      @Override
      @NonNull
      public List<User> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
          final int _cursorIndexOfProfilePictureUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profilePictureUrl");
          final int _cursorIndexOfDateOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "dateOfBirth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfHeightCm = CursorUtil.getColumnIndexOrThrow(_cursor, "heightCm");
          final int _cursorIndexOfWeightKg = CursorUtil.getColumnIndexOrThrow(_cursor, "weightKg");
          final int _cursorIndexOfBloodType = CursorUtil.getColumnIndexOrThrow(_cursor, "bloodType");
          final int _cursorIndexOfMedicalId = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalId");
          final int _cursorIndexOfAllergies = CursorUtil.getColumnIndexOrThrow(_cursor, "allergies");
          final int _cursorIndexOfMedicalConditions = CursorUtil.getColumnIndexOrThrow(_cursor, "medicalConditions");
          final int _cursorIndexOfOrganDonor = CursorUtil.getColumnIndexOrThrow(_cursor, "organDonor");
          final int _cursorIndexOfEmergencyContacts = CursorUtil.getColumnIndexOrThrow(_cursor, "emergencyContacts");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<User> _result = new ArrayList<User>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final User _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpDisplayName;
            _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
            final String _tmpProfilePictureUrl;
            if (_cursor.isNull(_cursorIndexOfProfilePictureUrl)) {
              _tmpProfilePictureUrl = null;
            } else {
              _tmpProfilePictureUrl = _cursor.getString(_cursorIndexOfProfilePictureUrl);
            }
            final Date _tmpDateOfBirth;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDateOfBirth)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDateOfBirth);
            }
            _tmpDateOfBirth = __converters.fromTimestamp(_tmp);
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final Float _tmpHeightCm;
            if (_cursor.isNull(_cursorIndexOfHeightCm)) {
              _tmpHeightCm = null;
            } else {
              _tmpHeightCm = _cursor.getFloat(_cursorIndexOfHeightCm);
            }
            final Float _tmpWeightKg;
            if (_cursor.isNull(_cursorIndexOfWeightKg)) {
              _tmpWeightKg = null;
            } else {
              _tmpWeightKg = _cursor.getFloat(_cursorIndexOfWeightKg);
            }
            final String _tmpBloodType;
            if (_cursor.isNull(_cursorIndexOfBloodType)) {
              _tmpBloodType = null;
            } else {
              _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            }
            final String _tmpMedicalId;
            if (_cursor.isNull(_cursorIndexOfMedicalId)) {
              _tmpMedicalId = null;
            } else {
              _tmpMedicalId = _cursor.getString(_cursorIndexOfMedicalId);
            }
            final String _tmpAllergies;
            if (_cursor.isNull(_cursorIndexOfAllergies)) {
              _tmpAllergies = null;
            } else {
              _tmpAllergies = _cursor.getString(_cursorIndexOfAllergies);
            }
            final String _tmpMedicalConditions;
            if (_cursor.isNull(_cursorIndexOfMedicalConditions)) {
              _tmpMedicalConditions = null;
            } else {
              _tmpMedicalConditions = _cursor.getString(_cursorIndexOfMedicalConditions);
            }
            final boolean _tmpOrganDonor;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfOrganDonor);
            _tmpOrganDonor = _tmp_1 != 0;
            final String _tmpEmergencyContacts;
            if (_cursor.isNull(_cursorIndexOfEmergencyContacts)) {
              _tmpEmergencyContacts = null;
            } else {
              _tmpEmergencyContacts = _cursor.getString(_cursorIndexOfEmergencyContacts);
            }
            final Date _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            final Date _tmpUpdatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfUpdatedAt);
            }
            final Date _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_5;
            }
            final boolean _tmpIsSynced;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_6 != 0;
            _item = new User(_tmpId,_tmpEmail,_tmpDisplayName,_tmpProfilePictureUrl,_tmpDateOfBirth,_tmpGender,_tmpHeightCm,_tmpWeightKg,_tmpBloodType,_tmpMedicalId,_tmpAllergies,_tmpMedicalConditions,_tmpOrganDonor,_tmpEmergencyContacts,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
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
