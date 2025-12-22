package com.healthtracker.app.di;

import com.healthtracker.app.data.local.AppDatabase;
import com.healthtracker.app.data.local.dao.ActivityRecordDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DatabaseModule_ProvideActivityRecordDaoFactory implements Factory<ActivityRecordDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideActivityRecordDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ActivityRecordDao get() {
    return provideActivityRecordDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideActivityRecordDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideActivityRecordDaoFactory(databaseProvider);
  }

  public static ActivityRecordDao provideActivityRecordDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideActivityRecordDao(database));
  }
}
