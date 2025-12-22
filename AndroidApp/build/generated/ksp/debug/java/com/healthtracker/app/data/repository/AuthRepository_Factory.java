package com.healthtracker.app.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.data.local.dao.UserDao;
import com.healthtracker.app.data.remote.FirebaseService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<UserDao> userDaoProvider;

  private final Provider<FirebaseService> firebaseServiceProvider;

  public AuthRepository_Factory(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<UserDao> userDaoProvider, Provider<FirebaseService> firebaseServiceProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.userDaoProvider = userDaoProvider;
    this.firebaseServiceProvider = firebaseServiceProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(firebaseAuthProvider.get(), userDaoProvider.get(), firebaseServiceProvider.get());
  }

  public static AuthRepository_Factory create(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<UserDao> userDaoProvider, Provider<FirebaseService> firebaseServiceProvider) {
    return new AuthRepository_Factory(firebaseAuthProvider, userDaoProvider, firebaseServiceProvider);
  }

  public static AuthRepository newInstance(FirebaseAuth firebaseAuth, UserDao userDao,
      FirebaseService firebaseService) {
    return new AuthRepository(firebaseAuth, userDao, firebaseService);
  }
}
