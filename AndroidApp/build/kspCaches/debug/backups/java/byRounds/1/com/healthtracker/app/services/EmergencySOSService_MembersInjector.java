package com.healthtracker.app.services;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.local.dao.UserDao;
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
public final class EmergencySOSService_MembersInjector implements MembersInjector<EmergencySOSService> {
  private final Provider<UserDao> userDaoProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public EmergencySOSService_MembersInjector(Provider<UserDao> userDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.userDaoProvider = userDaoProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<EmergencySOSService> create(Provider<UserDao> userDaoProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new EmergencySOSService_MembersInjector(userDaoProvider, firebaseAuthProvider);
  }

  @Override
  public void injectMembers(EmergencySOSService instance) {
    injectUserDao(instance, userDaoProvider.get());
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.services.EmergencySOSService.userDao")
  public static void injectUserDao(EmergencySOSService instance, UserDao userDao) {
    instance.userDao = userDao;
  }

  @InjectedFieldSignature("com.healthtracker.app.services.EmergencySOSService.firebaseAuth")
  public static void injectFirebaseAuth(EmergencySOSService instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
