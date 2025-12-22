package com.healthtracker.app.services;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.local.dao.ActivityRecordDao;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class StepCounterService_MembersInjector implements MembersInjector<StepCounterService> {
  private final Provider<ActivityRecordDao> activityRecordDaoProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public StepCounterService_MembersInjector(Provider<ActivityRecordDao> activityRecordDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.activityRecordDaoProvider = activityRecordDaoProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<StepCounterService> create(
      Provider<ActivityRecordDao> activityRecordDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new StepCounterService_MembersInjector(activityRecordDaoProvider, firebaseAuthProvider);
  }

  @Override
  public void injectMembers(StepCounterService instance) {
    injectActivityRecordDao(instance, activityRecordDaoProvider.get());
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.services.StepCounterService.activityRecordDao")
  public static void injectActivityRecordDao(StepCounterService instance,
      ActivityRecordDao activityRecordDao) {
    instance.activityRecordDao = activityRecordDao;
  }

  @InjectedFieldSignature("com.healthtracker.app.services.StepCounterService.firebaseAuth")
  public static void injectFirebaseAuth(StepCounterService instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
