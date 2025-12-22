package com.healthtracker.app.ui.splash;

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
public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<UserDao> userDaoProvider;

  public SplashActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<UserDao> userDaoProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.userDaoProvider = userDaoProvider;
  }

  public static MembersInjector<SplashActivity> create(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<UserDao> userDaoProvider) {
    return new SplashActivity_MembersInjector(firebaseAuthProvider, userDaoProvider);
  }

  @Override
  public void injectMembers(SplashActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
    injectUserDao(instance, userDaoProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.splash.SplashActivity.firebaseAuth")
  public static void injectFirebaseAuth(SplashActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.splash.SplashActivity.userDao")
  public static void injectUserDao(SplashActivity instance, UserDao userDao) {
    instance.userDao = userDao;
  }
}
