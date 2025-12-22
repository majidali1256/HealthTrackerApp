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
import com.healthtracker.app.data.local.entities.Document;
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
public final class DocumentDao_Impl implements DocumentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Document> __insertionAdapterOfDocument;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Document> __deletionAdapterOfDocument;

  private final EntityDeletionOrUpdateAdapter<Document> __updateAdapterOfDocument;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllByUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSyncStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCloudUrl;

  public DocumentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDocument = new EntityInsertionAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `documents` (`id`,`userId`,`title`,`description`,`category`,`fileName`,`filePath`,`fileSize`,`mimeType`,`isEncrypted`,`encryptionKeyAlias`,`cloudStorageUrl`,`documentDate`,`providerName`,`tags`,`uploadedAt`,`isSynced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getFileName());
        statement.bindString(7, entity.getFilePath());
        statement.bindLong(8, entity.getFileSize());
        statement.bindString(9, entity.getMimeType());
        final int _tmp = entity.isEncrypted() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getEncryptionKeyAlias() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getEncryptionKeyAlias());
        }
        if (entity.getCloudStorageUrl() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getCloudStorageUrl());
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getDocumentDate());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_1);
        }
        if (entity.getProviderName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getProviderName());
        }
        if (entity.getTags() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getTags());
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getUploadedAt());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_2);
        }
        final int _tmp_3 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_3);
      }
    };
    this.__deletionAdapterOfDocument = new EntityDeletionOrUpdateAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `documents` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfDocument = new EntityDeletionOrUpdateAdapter<Document>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `documents` SET `id` = ?,`userId` = ?,`title` = ?,`description` = ?,`category` = ?,`fileName` = ?,`filePath` = ?,`fileSize` = ?,`mimeType` = ?,`isEncrypted` = ?,`encryptionKeyAlias` = ?,`cloudStorageUrl` = ?,`documentDate` = ?,`providerName` = ?,`tags` = ?,`uploadedAt` = ?,`isSynced` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Document entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        statement.bindString(5, entity.getCategory());
        statement.bindString(6, entity.getFileName());
        statement.bindString(7, entity.getFilePath());
        statement.bindLong(8, entity.getFileSize());
        statement.bindString(9, entity.getMimeType());
        final int _tmp = entity.isEncrypted() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getEncryptionKeyAlias() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getEncryptionKeyAlias());
        }
        if (entity.getCloudStorageUrl() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getCloudStorageUrl());
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getDocumentDate());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_1);
        }
        if (entity.getProviderName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getProviderName());
        }
        if (entity.getTags() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getTags());
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getUploadedAt());
        if (_tmp_2 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_2);
        }
        final int _tmp_3 = entity.isSynced() ? 1 : 0;
        statement.bindLong(17, _tmp_3);
        statement.bindLong(18, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM documents WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllByUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM documents WHERE userId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSyncStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE documents SET isSynced = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateCloudUrl = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE documents SET cloudStorageUrl = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Document document, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDocument.insertAndReturnId(document);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Document document, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDocument.handle(document);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Document document, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDocument.handle(document);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
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
          __preparedStmtOfDeleteById.release(_stmt);
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
  public Object updateCloudUrl(final long id, final String url,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCloudUrl.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, url);
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
          __preparedStmtOfUpdateCloudUrl.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super Document> $completion) {
    final String _sql = "SELECT * FROM documents WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Document>() {
      @Override
      @Nullable
      public Document call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "isEncrypted");
          final int _cursorIndexOfEncryptionKeyAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionKeyAlias");
          final int _cursorIndexOfCloudStorageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudStorageUrl");
          final int _cursorIndexOfDocumentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "documentDate");
          final int _cursorIndexOfProviderName = CursorUtil.getColumnIndexOrThrow(_cursor, "providerName");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUploadedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final Document _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final boolean _tmpIsEncrypted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp != 0;
            final String _tmpEncryptionKeyAlias;
            if (_cursor.isNull(_cursorIndexOfEncryptionKeyAlias)) {
              _tmpEncryptionKeyAlias = null;
            } else {
              _tmpEncryptionKeyAlias = _cursor.getString(_cursorIndexOfEncryptionKeyAlias);
            }
            final String _tmpCloudStorageUrl;
            if (_cursor.isNull(_cursorIndexOfCloudStorageUrl)) {
              _tmpCloudStorageUrl = null;
            } else {
              _tmpCloudStorageUrl = _cursor.getString(_cursorIndexOfCloudStorageUrl);
            }
            final Date _tmpDocumentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDocumentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDocumentDate);
            }
            _tmpDocumentDate = __converters.fromTimestamp(_tmp_1);
            final String _tmpProviderName;
            if (_cursor.isNull(_cursorIndexOfProviderName)) {
              _tmpProviderName = null;
            } else {
              _tmpProviderName = _cursor.getString(_cursorIndexOfProviderName);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final Date _tmpUploadedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUploadedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUploadedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUploadedAt = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _result = new Document(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpFileName,_tmpFilePath,_tmpFileSize,_tmpMimeType,_tmpIsEncrypted,_tmpEncryptionKeyAlias,_tmpCloudStorageUrl,_tmpDocumentDate,_tmpProviderName,_tmpTags,_tmpUploadedAt,_tmpIsSynced);
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
  public Flow<List<Document>> getAllByUserFlow(final String userId) {
    final String _sql = "SELECT * FROM documents WHERE userId = ? ORDER BY uploadedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "isEncrypted");
          final int _cursorIndexOfEncryptionKeyAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionKeyAlias");
          final int _cursorIndexOfCloudStorageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudStorageUrl");
          final int _cursorIndexOfDocumentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "documentDate");
          final int _cursorIndexOfProviderName = CursorUtil.getColumnIndexOrThrow(_cursor, "providerName");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUploadedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final boolean _tmpIsEncrypted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp != 0;
            final String _tmpEncryptionKeyAlias;
            if (_cursor.isNull(_cursorIndexOfEncryptionKeyAlias)) {
              _tmpEncryptionKeyAlias = null;
            } else {
              _tmpEncryptionKeyAlias = _cursor.getString(_cursorIndexOfEncryptionKeyAlias);
            }
            final String _tmpCloudStorageUrl;
            if (_cursor.isNull(_cursorIndexOfCloudStorageUrl)) {
              _tmpCloudStorageUrl = null;
            } else {
              _tmpCloudStorageUrl = _cursor.getString(_cursorIndexOfCloudStorageUrl);
            }
            final Date _tmpDocumentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDocumentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDocumentDate);
            }
            _tmpDocumentDate = __converters.fromTimestamp(_tmp_1);
            final String _tmpProviderName;
            if (_cursor.isNull(_cursorIndexOfProviderName)) {
              _tmpProviderName = null;
            } else {
              _tmpProviderName = _cursor.getString(_cursorIndexOfProviderName);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final Date _tmpUploadedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUploadedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUploadedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUploadedAt = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new Document(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpFileName,_tmpFilePath,_tmpFileSize,_tmpMimeType,_tmpIsEncrypted,_tmpEncryptionKeyAlias,_tmpCloudStorageUrl,_tmpDocumentDate,_tmpProviderName,_tmpTags,_tmpUploadedAt,_tmpIsSynced);
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
  public Flow<List<Document>> getByCategoryFlow(final String userId, final String category) {
    final String _sql = "SELECT * FROM documents WHERE userId = ? AND category = ? ORDER BY uploadedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "isEncrypted");
          final int _cursorIndexOfEncryptionKeyAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionKeyAlias");
          final int _cursorIndexOfCloudStorageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudStorageUrl");
          final int _cursorIndexOfDocumentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "documentDate");
          final int _cursorIndexOfProviderName = CursorUtil.getColumnIndexOrThrow(_cursor, "providerName");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUploadedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final boolean _tmpIsEncrypted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp != 0;
            final String _tmpEncryptionKeyAlias;
            if (_cursor.isNull(_cursorIndexOfEncryptionKeyAlias)) {
              _tmpEncryptionKeyAlias = null;
            } else {
              _tmpEncryptionKeyAlias = _cursor.getString(_cursorIndexOfEncryptionKeyAlias);
            }
            final String _tmpCloudStorageUrl;
            if (_cursor.isNull(_cursorIndexOfCloudStorageUrl)) {
              _tmpCloudStorageUrl = null;
            } else {
              _tmpCloudStorageUrl = _cursor.getString(_cursorIndexOfCloudStorageUrl);
            }
            final Date _tmpDocumentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDocumentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDocumentDate);
            }
            _tmpDocumentDate = __converters.fromTimestamp(_tmp_1);
            final String _tmpProviderName;
            if (_cursor.isNull(_cursorIndexOfProviderName)) {
              _tmpProviderName = null;
            } else {
              _tmpProviderName = _cursor.getString(_cursorIndexOfProviderName);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final Date _tmpUploadedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUploadedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUploadedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUploadedAt = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new Document(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpFileName,_tmpFilePath,_tmpFileSize,_tmpMimeType,_tmpIsEncrypted,_tmpEncryptionKeyAlias,_tmpCloudStorageUrl,_tmpDocumentDate,_tmpProviderName,_tmpTags,_tmpUploadedAt,_tmpIsSynced);
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
  public Flow<List<Document>> searchDocumentsFlow(final String userId, final String query) {
    final String _sql = "SELECT * FROM documents WHERE userId = ? AND (title LIKE '%' || ? || '%' OR tags LIKE '%' || ? || '%') ORDER BY uploadedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"documents"}, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "isEncrypted");
          final int _cursorIndexOfEncryptionKeyAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionKeyAlias");
          final int _cursorIndexOfCloudStorageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudStorageUrl");
          final int _cursorIndexOfDocumentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "documentDate");
          final int _cursorIndexOfProviderName = CursorUtil.getColumnIndexOrThrow(_cursor, "providerName");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUploadedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final boolean _tmpIsEncrypted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp != 0;
            final String _tmpEncryptionKeyAlias;
            if (_cursor.isNull(_cursorIndexOfEncryptionKeyAlias)) {
              _tmpEncryptionKeyAlias = null;
            } else {
              _tmpEncryptionKeyAlias = _cursor.getString(_cursorIndexOfEncryptionKeyAlias);
            }
            final String _tmpCloudStorageUrl;
            if (_cursor.isNull(_cursorIndexOfCloudStorageUrl)) {
              _tmpCloudStorageUrl = null;
            } else {
              _tmpCloudStorageUrl = _cursor.getString(_cursorIndexOfCloudStorageUrl);
            }
            final Date _tmpDocumentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDocumentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDocumentDate);
            }
            _tmpDocumentDate = __converters.fromTimestamp(_tmp_1);
            final String _tmpProviderName;
            if (_cursor.isNull(_cursorIndexOfProviderName)) {
              _tmpProviderName = null;
            } else {
              _tmpProviderName = _cursor.getString(_cursorIndexOfProviderName);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final Date _tmpUploadedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUploadedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUploadedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUploadedAt = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new Document(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpFileName,_tmpFilePath,_tmpFileSize,_tmpMimeType,_tmpIsEncrypted,_tmpEncryptionKeyAlias,_tmpCloudStorageUrl,_tmpDocumentDate,_tmpProviderName,_tmpTags,_tmpUploadedAt,_tmpIsSynced);
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
  public Object getUnsyncedDocuments(final Continuation<? super List<Document>> $completion) {
    final String _sql = "SELECT * FROM documents WHERE isSynced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Document>>() {
      @Override
      @NonNull
      public List<Document> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "fileName");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfIsEncrypted = CursorUtil.getColumnIndexOrThrow(_cursor, "isEncrypted");
          final int _cursorIndexOfEncryptionKeyAlias = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptionKeyAlias");
          final int _cursorIndexOfCloudStorageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "cloudStorageUrl");
          final int _cursorIndexOfDocumentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "documentDate");
          final int _cursorIndexOfProviderName = CursorUtil.getColumnIndexOrThrow(_cursor, "providerName");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfUploadedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "uploadedAt");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "isSynced");
          final List<Document> _result = new ArrayList<Document>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Document _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final boolean _tmpIsEncrypted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEncrypted);
            _tmpIsEncrypted = _tmp != 0;
            final String _tmpEncryptionKeyAlias;
            if (_cursor.isNull(_cursorIndexOfEncryptionKeyAlias)) {
              _tmpEncryptionKeyAlias = null;
            } else {
              _tmpEncryptionKeyAlias = _cursor.getString(_cursorIndexOfEncryptionKeyAlias);
            }
            final String _tmpCloudStorageUrl;
            if (_cursor.isNull(_cursorIndexOfCloudStorageUrl)) {
              _tmpCloudStorageUrl = null;
            } else {
              _tmpCloudStorageUrl = _cursor.getString(_cursorIndexOfCloudStorageUrl);
            }
            final Date _tmpDocumentDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDocumentDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDocumentDate);
            }
            _tmpDocumentDate = __converters.fromTimestamp(_tmp_1);
            final String _tmpProviderName;
            if (_cursor.isNull(_cursorIndexOfProviderName)) {
              _tmpProviderName = null;
            } else {
              _tmpProviderName = _cursor.getString(_cursorIndexOfProviderName);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final Date _tmpUploadedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfUploadedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfUploadedAt);
            }
            final Date _tmp_3 = __converters.fromTimestamp(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.util.Date', but it was NULL.");
            } else {
              _tmpUploadedAt = _tmp_3;
            }
            final boolean _tmpIsSynced;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_4 != 0;
            _item = new Document(_tmpId,_tmpUserId,_tmpTitle,_tmpDescription,_tmpCategory,_tmpFileName,_tmpFilePath,_tmpFileSize,_tmpMimeType,_tmpIsEncrypted,_tmpEncryptionKeyAlias,_tmpCloudStorageUrl,_tmpDocumentDate,_tmpProviderName,_tmpTags,_tmpUploadedAt,_tmpIsSynced);
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
